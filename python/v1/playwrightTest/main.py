from c.sync_api import Playwright, sync_playwright, expect
import ddddocr
import os
from PIL import Image

# datas=[('./onnxruntime_providers_shared.dll','onnxruntime\\capi'),('./common.onnx','ddddocr'),('./common_old.onnx','ddddocr')],

def read_verify_code(page, locator, file_path):
    page.locator(locator).screenshot(path=file_path)

    # 打开保存下来的图片
    im1 = Image.open(file_path)
    # 为了使图片的识别率更高，可以处理一下图片
    imRGBA = im1.convert("RGBA")
    imL = imRGBA.convert("L")
    imL.save(file_path)
    ocr = ddddocr.DdddOcr()
    with open(file_path, 'rb') as f:
        img_bytes = f.read()
    text = ocr.classification(img_bytes)

    print('程序识别验证码: ', text)

    return text

def run(playwright: Playwright) -> None:
    # browser = playwright.chromium.launch(headless=False)
    # browser = playwright.chromium.launch_persistent_context(
    #     executable_path='/Applications/Google Chrome.app',
    #     user_data_dir='/Users/dreamli/Workspace/Python/v1/playwrightTest/temp',
    #     headless=False,
    #     args=['--disable-blink-features=AutomationControlled']
    # )
    # browser = playwright.chromium.launch(channel= 'chrome', headless=False)
    browser = playwright.firefox.launch(channel='firefox', headless=False)
    context = browser.new_context()
    page = context.new_page()
    page.goto("http://106.75.70.103/log/login")
    page.get_by_placeholder("请输入用户名").click()
    page.get_by_placeholder("请输入用户名").fill("root")
    page.get_by_placeholder("请输入用户名").press("Tab")
    page.get_by_placeholder("请输入登录密码").click()
    page.get_by_placeholder("请输入登录密码").fill("Pass@123")
    page.get_by_placeholder("请输入验证码").click()

    tempDir = os.getenv('TEMP')
    print("tempDir: ", tempDir)
    text = read_verify_code(page, '//img[@alt="验证码"]', "{}/ly-log-验证码.png".format(tempDir))

    page.get_by_placeholder("请输入验证码").fill(text)

    page.get_by_role("button", name="登 录").click()
    page.get_by_role("tab", name="海上平海风电场").click()
    page.get_by_role("button", name="导出pdf").click()

    print("等待下载")

    page.wait_for_timeout(timeout=15000)

    # ---------------------
    context.close()
    browser.close()

with sync_playwright() as playwright:
    run(playwright)
