from playwright.sync_api import Playwright, sync_playwright, expect


def run(playwright: Playwright) -> None:
    browser = playwright.chromium.launch(headless=False)
    context = browser.new_context()
    page = context.new_page()
    page.goto("http://10.170.69.214:8080/cas/login")
    page.locator("#username").click()
    page.locator("#username").fill("12081154")
    page.locator("#password").click()
    page.locator("#password").fill("Szw6215987!@#")
    page.locator("form").get_by_text("登录").click()
    with page.expect_popup() as page1_info:
        page.get_by_role("link", name="运 生产监控系统").click()
    page1 = page1_info.value
    page1.locator("div:nth-child(10) > .menuItemIcon > .icon > .iconfont").click()
    page1.get_by_text("风电机组运行情况表").click()
    page1.frame_locator("#my-iframe").locator("div:nth-child(12) > .fr-trigger-text > .fr-trigger-texteditor").click()
    page1.frame_locator("#my-iframe").locator("div:nth-child(12) > .fr-trigger-text > .fr-trigger-texteditor").fill("海上")
    page1.frame_locator("#my-iframe").get_by_text("海上").click()
    page1.frame_locator("#my-iframe").locator("div:nth-child(12) > .fr-trigger-text > .fr-trigger-texteditor").press("Enter")
    page1.frame_locator("#my-iframe").get_by_role("button", name="查 询").click()

    page1.wait_for_timeout(timeout=2000)

    page1.frame_locator("#my-iframe").locator(".linkspan[link*='H4项目']").click()
    page1.frame_locator("#my-iframe").get_by_role("button", name="导入模版数据").click()
    page1.frame_locator("#my-iframe").frame_locator("#inp").locator("input[name=\"file\"]").click()
    page1.frame_locator("#my-iframe").frame_locator("#inp").locator("input[name=\"file\"]").set_input_files("H4项目.xlsx")
    page1.frame_locator("#my-iframe").frame_locator("#inp").get_by_role("button", name="提交").click()

    page1.wait_for_timeout(timeout=5000)

    # ---------------------
    context.close()
    browser.close()


with sync_playwright() as playwright:
    run(playwright)
