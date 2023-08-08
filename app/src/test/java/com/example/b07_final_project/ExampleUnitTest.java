package com.example.b07_final_project;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;





/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    @Mock
    private LoginActivityView view;

    @Mock
    private LoginModel model;

    @Test
    public void checkEmptyUsername(){
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.checkFields("", "password");
        verify(view).showSnackbar("Fields cannot be empty");
    }
    @Test
    public void checkEmptyPassword(){
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.checkFields("username", "");
        verify(view).showSnackbar("Fields cannot be empty");
    }
    @Test
    public void checkFilledFields(){
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.checkFields("username", "password");
        verify(model).queryOwners(presenter, "username", "password");
    }
    @Test
    public void testCheckOwners_OwnerExist_PasswordMatch() {
        // Mock the model to return a valid database password
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.checkOwners(true, "valid_username", "correct_password");
        verify(model).getOwnerPassword(any(), eq("valid_username"), eq("correct_password"));
    }
    @Test
    public void testCheckOwners_IsNotAnOwner() {
        // Mock the model so owner doesn't exist but could be shopper or someone that doesn't exist
        // at all.
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.checkOwners(false, "potential_valid_username",
                "any_password");
        //This is to make sure the code under if doesn't get called, can remove if wanted.
        verify(model, never()).getOwnerPassword(any(), eq("potential_valid_username"),
                eq("any_password"));
        verify(model).queryShoppers(any(), eq("potential_valid_username"),
                eq("any_password"));
    }

    @Test
    public void testCheckOwnerPassword_PasswordMatch() {
        // Call the method with correct password
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.checkOwnerPassword("valid_username", "correct_password", "correct_password");
        verify(model).getOwnerStore(any(), eq("valid_username"));
    }

    @Test
    public void testCheckOwnerPassword_PasswordMismatch() {
        // Call the method with incorrect password
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.checkOwnerPassword("valid_username", "incorrect_password", "correct_password");
        verify(view).showSnackbar("Incorrect password");
    }

    @Test
    public void testCheckOwnerStore_HasStore() {
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.checkOwnerStore("valid_store");
        verify(view).startNewActivity(OwnerMenuActivity.class);
    }

    @Test
    public void testCheckOwnerStore_HasNoStore() {
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.checkOwnerStore(null);
        verify(view).startNewActivity(CreateStoreActivity.class);

    }
    @Test
    public void testCheckShopper_ShopperExist_PasswordMatch() {
        // Mock the model to return a valid database password
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.checkShoppers(true, "valid_username", "correct_password");
        verify(model).getShopperPassword(any(), eq("valid_username"), eq("correct_password"));
    }
    @Test
    public void testCheckShopper_ShopperExists_PasswordMismatch(){
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.checkShoppers(true, "valid_username", "incorrect_password");
        presenter.checkShopperPassword("valid_username", "incorrect Password", "correct_password");
        verify(view).showSnackbar("Incorrect password");
    }

    @Test
    public void testCheckShopper_IsNotAShopper() {
        // test if shopper doesn't exist so it queries the owners

        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.checkShoppers(false, "valid_username",
                "any_password");
        verify(view).showSnackbar("Username does not exist");

    }

   @Test
    public void testCheckShopperPassword_PasswordMismatch() {
        // Call the method with incorrect password
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.checkShopperPassword("valid_username", "incorrect_password", "correct_password");
        verify(view).showSnackbar("Incorrect password");
    }

    @Test
    public void testCheckShopperPassword_PasswordMatch() {
        // Call the method with correct password
        LoginPresenter presenter = new LoginPresenter(view, model);
        presenter.checkShopperPassword("valid_username", "correct_password", "correct_password");
        verify(view).startNewActivity(CustomerMenuActivity.class);


    }

}