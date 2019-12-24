package com.seerlogics.botadmin.service;

import com.lingoace.common.exception.GeneralErrorException;
import com.lingoace.spring.service.BaseServiceImpl;
import com.seerlogics.botadmin.exception.ErrorCodes;
import com.seerlogics.commons.dto.SearchBots;
import com.seerlogics.commons.dto.SearchIntents;
import com.seerlogics.commons.dto.SearchTrainedModel;
import com.seerlogics.commons.exception.BaseRuntimeException;
import com.seerlogics.commons.model.Account;
import com.seerlogics.commons.model.Category;
import com.seerlogics.commons.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by bkane on 11/1/18.
 */
@Service
@Transactional("botAdminTransactionManager")
@PreAuthorize("hasAnyRole('ACCT_ADMIN', 'UBER_ADMIN')")
public class CategoryService extends BaseServiceImpl<Category> {

    private final CategoryRepository categoryRepository;
    private final AccountService accountService;
    private final HelperService helperService;
    private final BotRepository botRepository;
    private final TrainedModelRepository trainedModelRepository;
    private final IntentRepository intentRepository;

    public CategoryService(CategoryRepository categoryRepository, AccountService accountService,
                           HelperService helperService, BotRepository botRepository,
                           TrainedModelRepository trainedModelRepository, IntentRepository intentRepository) {
        this.categoryRepository = categoryRepository;
        this.accountService = accountService;
        this.helperService = helperService;
        this.botRepository = botRepository;
        this.trainedModelRepository = trainedModelRepository;
        this.intentRepository = intentRepository;
    }

    @Override
    public Collection<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Collection<Category> findForEdit() {
        List<Category> categoryList = categoryRepository.findByOwnerAccounts(accountService.getAuthenticatedUser());

        for (Category currentCategory : categoryList) {
            currentCategory.setDeleteAllowed(isAllowedToDelete(currentCategory));
        }

        return categoryList;
    }

    @PreAuthorize("hasAnyRole('ACCT_ADMIN', 'UBER_ADMIN', 'ACCT_USER')")
    public Collection<Category> finaAllForSelection() {
        Account admin = accountService.getAccountByUsername("admin");
        return categoryRepository.findByOwnerAccounts(admin, accountService.getAuthenticatedUser());
    }

    @Override
    public Category getSingle(Long id) {
        return categoryRepository.getOne(id);
    }

    @Override
    public Category save(Category category) {
        if (StringUtils.isBlank(category.getCode())) {
            category.setCode("CAT-" + helperService.generateRandomCode());
        }
        // is new category?
        if (category.getId() == null) {
            category.setOwnerAccount(accountService.getAuthenticatedUser());
            return categoryRepository.save(category);
        } else {
            Category tempCat = categoryRepository.getOne(category.getId());
            if (this.helperService.isAllowedToEdit(tempCat.getOwnerAccount())) {
                return categoryRepository.save(category);
            } else {
                throw new BaseRuntimeException(ErrorCodes.UNAUTHORIZED_ACCESS);
            }
        }
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.getOne(id);

        if (!isAllowedToDelete(category)) {
            GeneralErrorException generalErrorException = new GeneralErrorException();
            generalErrorException.addError("notAllowedToDeleteCategory",
                    this.helperService.getMessage("message.category.cannot.delete", null), null);
            throw generalErrorException;
        }

        if (this.helperService.isAllowedToEdit(category.getOwnerAccount())) {
            categoryRepository.delete(category);
        } else {
            throw new BaseRuntimeException(ErrorCodes.UNAUTHORIZED_ACCESS);
        }
    }

    public boolean isAllowedToDelete(Category category) {
        SearchBots searchBots = new SearchBots();
        searchBots.setCategory(category);
        searchBots.setOwnerAccount(this.accountService.getAuthenticatedUser());

        SearchTrainedModel searchTrainedModel = new SearchTrainedModel();
        searchTrainedModel.setCategory(category);
        searchTrainedModel.setOwnerAccount(this.accountService.getAuthenticatedUser());

        SearchIntents searchIntents = new SearchIntents();
        searchIntents.setCategory(category);
        searchIntents.setOwnerAccount(this.accountService.getAuthenticatedUser());

        return this.botRepository.findBots(searchBots).isEmpty()
                && this.intentRepository.findIntentsAndUtterances(searchIntents).isEmpty()
                && this.trainedModelRepository.findTrainedModel(searchTrainedModel).isEmpty();
    }

    public Category initModel() {
        return new Category();
    }

    public Category getCategoryByCode(String code) {
        return categoryRepository.findByCode(code);
    }
}
