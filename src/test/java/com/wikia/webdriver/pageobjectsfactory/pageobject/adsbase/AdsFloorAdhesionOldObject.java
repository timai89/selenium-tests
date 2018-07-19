package com.wikia.webdriver.pageobjectsfactory.pageobject.adsbase;

import com.wikia.webdriver.common.logging.Log;

import org.openqa.selenium.*;

public class AdsFloorAdhesionOldObject extends AdsBaseObject {

  private static final String FLOOR_ADHESION_CSS = "#ext-wikia-adEngine-template-floor";
  private static final String
      FLOOR_ADHESION_AD_FRAME_CSS
      = "#ext-wikia-adEngine-template-floor .ad iframe";
  private static final String FLOOR_ADHESION_IMAGE_IN_FRAME_CSS = "img";
  private static final String
      FLOOR_ADHESION_CLOSE_CSS
      = "#ext-wikia-adEngine-template-floor .close";
  private static final String WIKIA_BAR_CSS = "#WikiaBar";
  private static final By FLOOR_ADHESION_CLOSE_SELECTOR = By.cssSelector(FLOOR_ADHESION_CLOSE_CSS);

  public AdsFloorAdhesionOldObject(WebDriver driver, String testedPage, Dimension resolution) {
    super(driver, testedPage, resolution);
  }

  public void verifyFloorAdhesionPresent(
      String expectedSlotName, String expectedLineItemId, String expectedCreativeId
  ) {
    verifyGptAdInSlot(expectedSlotName, expectedLineItemId, expectedCreativeId);
    wait.forElementVisible(By.cssSelector(FLOOR_ADHESION_CSS));
    Log.log("Check visibility of Floor Adhesion", "Floor Adhesion should be displayed", true);
  }

  public void verifyThereIsNoFloorAdhesion() {
    waitForElementNotVisibleByElement(driver.findElement(By.cssSelector(FLOOR_ADHESION_CSS)));
    Log.log("Check visibility", "Clicking Floor Adhesion close button hides ad unit", true);
  }

  public AdsFloorAdhesionOldObject clickFloorAdhesion() {
    WebElement iframeAd = driver.findElement(By.cssSelector(FLOOR_ADHESION_AD_FRAME_CSS));
    driver.switchTo().frame(iframeAd);
    driver.findElement(By.cssSelector(FLOOR_ADHESION_IMAGE_IN_FRAME_CSS)).click();
    driver.switchTo().defaultContent();
    return this;
  }

  public AdsFloorAdhesionOldObject clickFloorAdhesionModalClose(String floorAdhesionModalCloseSelector) {
    wait.forElementClickable(By.cssSelector(floorAdhesionModalCloseSelector)).click();
    return this;
  }

  public AdsFloorAdhesionOldObject clickFloorAdhesionClose() {
    wait.forElementPresent(FLOOR_ADHESION_CLOSE_SELECTOR).click();
    return this;
  }

  public void verifyModalOpened(String floorAdhesionModalSelector) {
    wait.forElementVisible(By.cssSelector(floorAdhesionModalSelector));
    Log.log("Check visibility", "Clicking Floor Adhesion opens light-box", true);
  }

  public void verifyThereIsNoModal(String floorAdhesionModalSelector) {
    wait.forElementNotPresent(By.cssSelector(floorAdhesionModalSelector));
    Log.log("Check visibility", "Clicking light-box close button hides light-box", true);
  }

  public void verifyThereIsNoWikiaBar(String browser) {
    if (driver.isChromeMobile()) {
      // Mercury does not have WikiaBar
      Log.log("Check visibility of Wikia Bar", "It is Mercury skin with no Wikia Bar", true);
      return;
    }

    waitForElementNotVisibleByElement(driver.findElement(By.cssSelector(WIKIA_BAR_CSS)));
    Log.log(
        "Check visibility of Wikia Bar",
        "There should be no Wikia Bar when Floor Adhesion is visible",
        true
    );
  }
}
