package controller;

import helper.CountryQuery;
import helper.FirstLevelDivisionQuery;
import helper.HelperFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Country;
import model.FirstLevelDivision;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static helper.CustomerQuery.insert;
import static helper.CustomerQuery.selectCustomersByNameAdd;
import static model.Main.homeScreen;
/**This class creates a controller for AddCustomer.fxml.  */
public class AddCustomerController implements Initializable {
    public TextField addCustomerAddressText;
    public TextField addCustomerIdText;
    public TextField addCustomerNameText;
    public ComboBox addCustomerFirsLevelDivisionCombo;
    public TextField addCustomerPostalText;
    public ComboBox addCustomerCountryCombo;
    public TextField addCustomerPhoneText;

    /**
     * The event handler method for the add button.
     * This method assigns the text field inputs to variables for use in the Customer class constructor.
     * The method uses various forms of logic to ensure the texts fields are all filled out and use the correct data types, if not a dialogue box is displayed with the error that needs to be corrected.
     * Selected variables are then passed into insert() and  selectCustomersByNameAdd() and then homeScreen() is called. The user is directed back to the home screen.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onAddCustomerButton(ActionEvent actionEvent) throws SQLException, IOException {
        if(addCustomerNameText.getText() == ""
                || addCustomerAddressText.getText() == ""
                || addCustomerPostalText.getText() == ""
                || addCustomerPhoneText.getText() == ""
                || addCustomerFirsLevelDivisionCombo.getSelectionModel().isEmpty())
        {
            HelperFunctions.errorDialogue("Add Customer Error", "Please Enter Data in All Text Fields");
            HelperFunctions.error.showAndWait();
        }
        else {
            String customerName = addCustomerNameText.getText();
            String address = addCustomerAddressText.getText();
            String postalCode = addCustomerPostalText.getText();
            String phoneNumber = addCustomerPhoneText.getText();
            int divisionId = ((FirstLevelDivision) addCustomerFirsLevelDivisionCombo.getSelectionModel().getSelectedItem()).getDivisionId();

            insert(customerName, address, postalCode, phoneNumber, divisionId);
            selectCustomersByNameAdd(customerName);
            homeScreen(actionEvent);
        }
    }

    /**
     * The event handler method for the Cancel button.
     * homeScreen() is called and the user is directed back to the home screen. No customer is created.
     * @param actionEvent
     * @throws IOException
     */
    public void onCancelAddCustomerButton(ActionEvent actionEvent) throws IOException {
        homeScreen(actionEvent);
    }

    /**
     * The event handler method for setting the First Level Division combo box.
     * The selection from the Country combo box is identified and used to set the corresponding divisions in the combo box via the setFldByCountry() method.
     * @param actionEvent
     * @throws SQLException
     */
    public void onCountrySelect(ActionEvent actionEvent) throws SQLException {
        if(addCustomerCountryCombo.getSelectionModel().getSelectedItem() == null){
            Country c = helper.CountryQuery.selectAllCountries().get(0);
            addCustomerCountryCombo.setValue(c);
        }
        else {
            int selCountryId = ((Country) addCustomerCountryCombo.getSelectionModel().getSelectedItem()).getCountryId();
            FirstLevelDivisionQuery.clearFLDByCountry();
            addCustomerFirsLevelDivisionCombo.setItems(helper.FirstLevelDivisionQuery.setFldByCountry(selCountryId));
        }
    }

    /**
     * The first method called when the AddCustomerController is initialized.
     * This method sets the data that will populate the combo boxes on the form.
     * A Try/Catch block is used for any SQLExceptions.
     * @param url
     * @param resourceBundle
     */
        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addCustomerCountryCombo.setItems(helper.CountryQuery.selectAllCountries());
            addCustomerFirsLevelDivisionCombo.placeholderProperty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
