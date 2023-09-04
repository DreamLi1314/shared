#include "httplib.h"
#include <iostream>

int main(void)
{
  httplib::Client cli("localhost", 8080);

  if (auto res = cli.Get("/litefllow/test01?chain=chain1&arg=aaaaa")) {
    if (res->status == 200) {
      std::cout << res->body << std::endl;
    }
  } else {
    auto err = res.error();
    std::cout << "HTTP error: " << httplib::to_string(err) << std::endl;
  }
}