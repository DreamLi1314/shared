#include <QApplication>  
#include <QLabel>  
#include <QLineEdit>  
#include <QPushButton>  
#include <QTextEdit>  
#include <QUrl>

int main(int argc, char *argv[])  
{
    QApplication app(argc, argv);

    QLabel label;  
    QLineEdit *urlInput = new QLineEdit(label);  
    QPushButton *sendRequestButton = new QPushButton("发送请求", label);  
    QTextEdit *responseTextEdit = new QTextEdit(this);

    connect(sendRequestButton, &QPushButton::clicked, [=]() {  
        QUrl requestUrl = urlInput->text();  
        QNetworkAccessManager manager;  
        connect(&manager, &QNetworkAccessManager::responseReceived, [=]() {  
            responseTextEdit->append("<h1>响应:</h1>");  
            responseTextEdit->append(manager.response().text());  
            responseTextEdit->append("<h1>请求 URL:</h1>");  
            responseTextEdit->append(requestUrl.toString());  
            responseTextEdit->append("<h1>响应头:</h1>");  
            for (int i = 0; i < manager.response().headerFields().count(); i++)  
                responseTextEdit->append(QString("%1: %2").arg(manager.response().headerFields().keyAt(i), manager.response().headerFields().valueAt(i).toString()));  
            responseTextEdit->append("<h1>响应体:</h1>");  
        });  
        manager.request(requestUrl);  
    });

    label.setSizePolicy(QSizePolicy::Preferred, QSizePolicy::Maximum);  
    urlInput->setSizePolicy(QSizePolicy::Preferred, QSizePolicy::Maximum);  
    sendRequestButton->setSizePolicy(QSizePolicy::Preferred, QSizePolicy::Maximum);  
    responseTextEdit->setSizePolicy(QSizePolicy::Preferred, QSizePolicy::Maximum);

    app.setApplicationName("HTTP 请求发送器");  
    app.setOrganizationName("Your Name");  
    app.setLogoFile("logo.png");  
    app.setVersion("1.0");  
    app.setWindowTitle("HTTP 请求发送器");  
    app.exec();

    return app.exec();  
}
