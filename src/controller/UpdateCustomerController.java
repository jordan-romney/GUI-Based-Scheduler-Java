package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.*;
import helper.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static helper.CustomerQuery.update;
import static model.Main.homeScreen;
/**This class creates a controller for UpdateCustomer.fxml. */
public class UpdateCustomerController implements Initializable {
    public TextField updateCustomerAddressText;
    public TextField updateCustomerIdText;
    public TextField updateCustomerNameText;
    public ComboBox updateCustomerFirsLevelDivisionCombo;
    public TextField updateCustomerPostalText;
    public ComboBox updateCustomerCountryCombo;
    public TextField updateCustomerPhoneText;
    public static Customer sc = null;
    public static int customerIndex = 0;

    /**
     * The method for passing the selected customer from the home screen.
     * The selected customer object and its index are set to variables in the controller for later use.
     * @param selection
     * @param selectedIndex
     */
    public static void passCustomerSelection(Customer selection, int selectedIndex) {
        sc = selection;
        customerIndex = selectedIndex;
    }

    /**
     * The event handler method for the update button.
     * This method assigns the text field inputs to variables for use in the Customer class constructor.
     * The method uses various forms of logic to ensure the texts fields are all filled out and use the correct data types, if not a dialogue box is displayed with the error that needs to be corrected.
     * Selected variables are then passed into update() and then homeScreen() is called. The user is directed back to the home screen.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onUpdateCustomerButton(ActionEvent actionEvent) throws SQLException, IOException {
        if(updateCustomerNameText.getText() == ""
                || updateCustomerAddressText.getText() == ""
                || updateCustomerPostalText.getText() == ""
                || updateCustomerPhoneText.getText() == ""
                || updateCustomerFirsLevelDivisionCombo.getValue().equals("Select a First-Level-Division")
                || updateCustomerFirsLevelDivisionCombo.getSelectionModel().getSelectedItem().equals(null))
        {
            HelperFunctions.errorDialogue("Add Customer Error", "Please Enter Data in All Text Fields");
            HelperFunctions.error.showAndWait();
        }
        else {
            int customerId = (Integer.parseInt(updateCustomerIdText.getText()));
            String customerName = updateCustomerNameText.getText();
            String address = updateCustomerAddressText.getText();
            String postalCode = updateCustomerPostalText.getText();
            String phoneNumber = updateCustomerPhoneText.getText();
            int divisionId = ((FirstLevelDivision) updateCustomerFirsLevelDivisionCombo.getSelectionModel().getSelectedItem()).getDivisionId();

            update(customerId, customerName, address, postalCode, phoneNumber, divisionId);
            homeScreen(actionEvent);
        }
    }

    /**
     * The event handler method for the Cancel button.
     * homeScreen() is called and the user is directed back to the home screen. No customer is updated.
     * @param actionEvent
     * @throws IOException
     */
    public void onCancelUpdateCustomerButton(ActionEvent actionEvent) throws IOException {
        homeScreen(actionEvent);
    }

    /**
     * The event handler method for setting the First Level Division combo box.
     * The selection from the Country combo box is identified and used to set the corresponding divisions in the combo box via the setFldByCountry() method.
     * The First Level Division value is changed to null.
     * @param actionEvent
     * @throws SQLException
     */
    public void onUpdateCountrySelect(ActionEvent actionEvent) throws SQLException {
        if(updateCustomerCountryCombo.getSelectionModel().getSelectedItem() == null){
            //ignore
        }
        else {
            int selCountryId = ((Country) updateCustomerCountryCombo.getSelectionModel().getSelectedItem()).getCountryId();
            FirstLevelDivisionQuery.clearFLDByCountry();
            updateCustomerFirsLevelDivisionCombo.setValue("Select a First-Level-Division");
            updateCustomerFirsLevelDivisionCombo.setItems(FirstLevelDivisionQuery.setFldByCountry(selCountryId));
        }
    }

    /**
     * The first method called when the UpdateACustomerController is initialized.
     * The combo boxes and text fields are populated with attributes from the selected appointment.
     * A Try/Catch block is used for any SQLExceptions.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Country country = null;
        try {
            updateCustomerCountryCombo.setItems(CountryQuery.selectAllCountries());
            System.out.println("init " +CountryQuery.selectAllCountries());
            updateCustomerFirsLevelDivisionCombo.setItems(FirstLevelDivisionQuery.setFldByCountry(HelperFunctions.findCountryById(sc.getDivisionId()).getCountryId()));
            updateCustomerFirsLevelDivisionCombo.setValue(HelperFunctions.findFLDById(sc.getDivisionId()));
            country = HelperFunctions.findCountryById(sc.getDivisionId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateCustomerIdText.setText(Integer.toString(sc.getCustomerId()));
        updateCustomerNameText.setText(sc.getCustomerName());
        updateCustomerAddressText.setText(sc.getAddress());
        updateCustomerCountryCombo.setValue(country);
        updateCustomerPostalText.setText(sc.getPostalCode());
        updateCustomerPhoneText.setText(sc.getPhoneNumber());
    }
}
