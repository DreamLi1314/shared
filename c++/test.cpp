#include <iostream>  
#include <string>  
#include <curl/curl.h>  
#include <unistd.h>

using namespace std;

class HTTPRequest {  
public:  
    HTTPRequest(const string& url) : m_url(url) {}

    void sendRequest() {  
        CURL *curl = curl_easy_init();  
        if (curl) {  
            curl_easy_setopt(curl, CURLOPT_URL, m_url.c_str());  
            curl_easy_perform(curl);  
            curl_easy_cleanup(curl);  
        }  
    }

    string getRequestData() {  
        string data;  
        data += "chain=" + to_string(m_chain);  
        data += "&arg=" + to_string(m_arg);  
        return data;  
    }

private:  
    string m_url;  
    int m_chain;  
    int m_arg;  
};

int main() {  
    HTTPRequest request("http://localhost:8080/litefllow/test01?chain=chain1&arg=aaaaa");  
    string response = request.getRequestData();  
    request.sendRequest();  
    return 0;  
}