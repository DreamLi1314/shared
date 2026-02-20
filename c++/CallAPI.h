#include <iostream>  
#include <string>  
#include <curl/curl.h>  
#include <curl/easy.h>

using namespace std;

class CallAPI {  
private:  
    CURL *curl;  
    CURLcode res;

public:  
    CallAPI() {  
        curl = curl_easy_init();  
        if (curl) {  
            curl_easy_setopt(curl, CURLOPT_URL, "http://192.168.2.56:8086/mem/API/getSingleValue?parameterId=1&heightLevel=0&longitude=110&latitude=20&modelType=3&dataTime=2023-06-13+03:00:00");  
            curl_easy_setopt(curl, CURLOPT_FOLLOWLOCATION, 1L);  
            curl_easy_setopt(curl, CURLOPT_COOKIEFILE, "");  
            res = curl_easy_perform(curl);  
            if (res != CURLE_OK) {  
                fprintf(stderr, "curl_easy_perform() failed: %s\n",  
                        curl_easy_strerror(res));  
            } else {  
                cout << "API call succeeded." << endl;  
            }  
        } else {  
            cout << "Failed to init curl." << endl;  
        }  
    }

    ~CallAPI() {  
        curl_easy_cleanup(curl);  
    }  
};  
