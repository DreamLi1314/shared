from playwright.sync_api import Playwright, sync_playwright, expect


def run(playwright: Playwright) -> None:
    browser = playwright.chromium.launch(headless=False)
    context = browser.new_context()
    page = context.new_page()
    page.goto("http://106.75.70.103/log/login")
    page.get_by_placeholder("请输入用户名").click()
    page.get_by_placeholder("请输入用户名").fill("root")
    page.get_by_placeholder("请输入用户名").press("Tab")
    page.get_by_placeholder("请输入登录密码").fill("Pass@123")
    page.get_by_placeholder("请输入验证码").click()
    page.get_by_placeholder("请输入验证码").fill("ccoe")
    page.get_by_role("button", name="登 录").click()
    page.get_by_role("menuitem", name="日报上传").click()
    page.get_by_text("调度中心日报").click()
    page.get_by_role("button", name="上 传").nth(1).click()
    page.get_by_role("button", name="上 传").nth(1).set_input_files("推送管理数据模板.xlsx")

    # ---------------------
    context.close()
    browser.close()


with sync_playwright() as playwright:
    run(playwright)
