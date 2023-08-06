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
    private LoginActivityView mockLoginView;

    @Mock
    private LoginModel mockModel;


    /*@Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }*/

    @Test
    public void testCheckOwners_OwnerExist_PasswordMatch() {
        // Mock the model to return a valid database password
        LoginPresenter presenter = new LoginPresenter(mockLoginView, mockModel);
        presenter.checkOwners(true, "valid_username", "correct_password");
        verify(mockModel).getOwnerPassword(any(), eq("valid_username"), eq("correct_password"));
    }
    @Test
    public void testCheckOwners_IsNotAnOwner() {
        // Mock the model so owner doesn't exist but could be shopper or someone that doesn't exist
        // at all.
        LoginPresenter presenter = new LoginPresenter(mockLoginView, mockModel);
        presenter.checkOwners(false, "potential_valid_username",
                "any_password");
        //This is to make sure the code under if doesn't get called, can remove if wanted.
        verify(mockModel, never()).getOwnerPassword(any(), eq("potential_valid_username"),
                eq("any_password"));
        verify(mockModel).queryShoppers(any(), eq("potential_valid_username"),
                eq("any_password"));
    }

    @Test
    public void testCheckOwnerPassword_PasswordMatch() {
        // Call the method with correct password
        LoginPresenter presenter = new LoginPresenter(mockLoginView, mockModel);
        presenter.checkOwnerPassword("valid_username", "correct_password", "correct_password");
        verify(mockModel).getOwnerStore(any(), eq("valid_username"));
    }

    @Test
    public void testCheckOwnerPassword_PasswordMismatch() {
        // Call the method with incorrect password
        LoginPresenter presenter = new LoginPresenter(mockLoginView, mockModel);
        presenter.checkOwnerPassword("valid_username", "incorrect_password", "correct_password");
        verify(mockLoginView).showSnackbar("Incorrect password");
    }

}