import ddddocr
from PIL import Image
import os
from PySide6 import QtCore
from PySide6.QtWidgets import QMessageBox

from PySide6.QtCore import Slot
from playwright.sync_api import Playwright, sync_playwright


class EventHandler:
    def __init__(self, ui):
        self.ui = ui
        self.register_slot()

    def register_slot(self):
        self.ui.pushButton.clicked.connect(self.on_pushButton_clicked)

    def read_verify_code(self, page, locator, file_path):
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

    def run(self, playwright: Playwright, channel) -> None:
        browser = playwright.chromium.launch(channel=channel, headless=False)
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
        text = self.read_verify_code(page, '//img[@alt="验证码"]', "{}/ly-log-验证码.png".format(tempDir))

        page.get_by_placeholder("请输入验证码").fill(text)

        page.get_by_role("button", name="登 录").click()
        page.get_by_role("menuitem", name="日报上传").click()
        page.get_by_text("调度中心日报").click()

        print("准备上传文件")

        # page.get_by_role("button", name="上 传").nth(1).click()

        page.wait_for_timeout(timeout=5000)

        page.locator(
            "div.flex-grow-0:nth-child(1) > span:nth-child(1) > div:nth-child(1) > span:nth-child(1) > input:nth-child(1)") \
            .set_input_files(r"/Users/dreamli/Downloads/推送管理数据模板2.xlsx")

        page.wait_for_timeout(timeout=5000)

        # ---------------------
        context.close()
        browser.close()

    # 自动报送触发逻辑
    @QtCore.Slot()
    def on_pushButton_clicked(self):
        channel = self.ui.buttonGroup.checkedButton().property("value")
        playwright = sync_playwright().start()

        self.run(playwright, channel)

        msg = QMessageBox(text="自动上报完成!")
        msg.setWindowTitle("成功")
        msg.setStandardButtons(QMessageBox.Ok)
        msg.show()
