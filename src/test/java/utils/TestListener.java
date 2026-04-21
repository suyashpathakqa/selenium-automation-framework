package utils;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.*;

import base.BaseTest;

public class TestListener extends BaseTest implements ITestListener {

    ExtentReports extent = ExtentManager.getReport();
    ExtentTest test;

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass("✅ Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.fail("❌ Test Failed");

        String path = ScreenshotUtils.captureScreenshot(driver, result.getName());

        test.addScreenCaptureFromPath(path);
    }

    @Override
    public void onFinish(org.testng.ITestContext context) {
        extent.flush();
    }
}