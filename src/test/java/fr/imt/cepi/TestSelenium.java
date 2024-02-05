package fr.imt.cepi;


import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
//import org.junit.Test;
//import org.openqa.selenium.WebDriver;

/**
 * Exemple de test sur le navigateur avec Selenium
 */
public class TestSelenium {
    // private WebDriver driver;

    @BeforeClass
    public static void setupClass() {

 //       FirefoxDriverManager.firefoxdriver().setup();
//        ChromeDriverManager.getInstance().setup();
    }

    @Before
    public void setUp() {

//        driver = new FirefoxDriver();
//        driver = new ChromeDriver();
//        driver = new SafariDriver();
    }

    @After
    public void tearDown() {
//        if (driver != null)
//            driver.quit();
    }

//    @Test
//    public void connectOK() throws Exception {
//        driver.get("http://localhost:8080/IJServ/");
//
//        WebDriverWait wait = new WebDriverWait(driver, 5);
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("connect")));
//
//        driver.findElement(By.name("nom")).sendKeys("name");
//        driver.findElement(By.name("prenom")).sendKeys("firstname");
//        driver.findElement(By.name("connect")).click();
//
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.alert-danger")));
//        assertEquals("Combinaison incorrecte.", driver.findElement(By.cssSelector("div.alert-danger")).getText());
//
//        assertEquals("Accueil", driver.getTitle());
//    }
}

