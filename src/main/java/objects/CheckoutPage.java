package objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {

	    public CheckoutPage(WebDriver driver) {
		super(driver);
	}

		@FindBy(id = "first-name")
	    private WebElement firstNameField;

	    @FindBy(id = "last-name")
	    private WebElement lastNameField;

	    @FindBy(id = "postal-code")
	    private WebElement postalCodeField;

	    @FindBy(id = "continue")
	    private WebElement continueButton;

	    @FindBy(id="finish")
	    private WebElement finishButton;

	    @FindBy(className ="complete-header")
	    private WebElement verifyMessage;



	    public void enterCheckoutInformation(String firstName, String lastName, String postalCode) {
	        firstNameField.sendKeys(firstName);
	        lastNameField.sendKeys(lastName);
	        postalCodeField.sendKeys(postalCode);
	        continueButton.click();
	        finishButton.click();

	    }

	    public String getThankYouMessageText() {
	        return verifyMessage.getText();
	    }

}
