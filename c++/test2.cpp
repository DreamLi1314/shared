#include <iostream>  
#include <string>  
#include <sstream>  
#include <curl/curl.h>  
#include <httpclient.h>


using namespace std;

class HTTPRequest {  
private:  
    string url_;  
    string username_;  
    string password_;  
    string charset_;  
    string method_;  
    string body_;  
    string contentType_;

public:  
    HTTPRequest(string url, string username, string password, string charset, string method, string body) {  
        this->url_ = url;  
        this->username_ = username;  
        this->password_ = password;  
        this->charset_ = charset;  
        this->method_ = method;  
        this->body_ = body;  
        this->contentType_ = "application/x-www-form-urlencoded";  
    }

    ~HTTPRequest() {  
    }

    void send() {  
        CURL *curl_ = curl_easy_init();  
        if (curl_) {  
            curl_easy_setopt(curl_, CURLOPT_URL, url_.c_str());  
            curl_easy_setopt(curl_, CURLOPT_USERPWD, username_ + ":" + password_);  
            curl_easy_setopt(curl_, CURLOPT_HTTPAUTH, CURLAUTH_ANY);  
            curl_easy_setopt(curl_, CURLOPT_SSL_VERIFYPEER, false);  
            curl_easy_setopt(curl_, CURLOPT_SSL_VERIFYHOST, false);  
            CURLcode res = curl_easy_perform(curl_);  
            if (res == CURLE_OK) {  
                string response_ = curl_easy_strerror(res);  
                cout << "Response: " << response_ << endl;  
            } else {  
                cout << "Error: " << res << endl;  
            }  
            curl_easy_cleanup(curl_);  
        }  
        curl_global_cleanup();  
    }

    string getResponse() {  
        string response_;  
        send();  
        response_ = stringstream(curl_easy_strerror(curl_easy_perform(curl_)) << endl)  
                         .str();  
        return response_;  
    }  
};  
