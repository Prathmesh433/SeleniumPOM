package objects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
	public HomePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[@class='inventory_item']")
	private List<WebElement> inventoryItems;

	@FindBy(xpath = "//button[text()='Add to cart']")
	private List<WebElement> addToCartButtons;

	@FindBy(xpath = "//a[@class='shopping_cart_link']")
	private WebElement cartbutton;

	public void addItemToCart(int index) {
		addToCartButtons.get(index).click();
	}
	public void clickaddtocart() {
		cartbutton.click();

	}
}
