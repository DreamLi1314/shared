# -*- coding: utf-8 -*-

################################################################################
## Form generated from reading UI file 'main.ui'
##
## Created by: Qt User Interface Compiler version 6.2.1
##
## WARNING! All changes made in this file will be lost when recompiling UI file!
################################################################################

from PySide6.QtCore import (QCoreApplication, QDate, QDateTime, QLocale,
    QMetaObject, QObject, QPoint, QRect,
    QSize, QTime, QUrl, Qt)
from PySide6.QtGui import (QBrush, QColor, QConicalGradient, QCursor,
    QFont, QFontDatabase, QGradient, QIcon,
    QImage, QKeySequence, QLinearGradient, QPainter,
    QPalette, QPixmap, QRadialGradient, QTransform)
from PySide6.QtWidgets import (QApplication, QButtonGroup, QLabel, QLayout,
    QMainWindow, QMenuBar, QPushButton, QRadioButton,
    QSizePolicy, QStatusBar, QVBoxLayout, QWidget)
import main_rc

class Ui_MainWindow(object):
    def setupUi(self, MainWindow):
        if not MainWindow.objectName():
            MainWindow.setObjectName(u"MainWindow")
        MainWindow.resize(1438, 718)
        MainWindow.setAutoFillBackground(False)
        MainWindow.setStyleSheet(u"")
        self.container = QWidget(MainWindow)
        self.container.setObjectName(u"container")
        self.container.setAutoFillBackground(False)
        self.container.setStyleSheet(u"#container{background-image: url(:/assets/imgs/bg2.png);}")
        self.pushButton = QPushButton(self.container)
        self.pushButton.setObjectName(u"pushButton")
        self.pushButton.setGeometry(QRect(620, 510, 221, 41))
        font = QFont()
        font.setPointSize(24)
        self.pushButton.setFont(font)
        self.pushButton.setStyleSheet(u"background-color: rgb(0, 249, 0);")
        self.layoutWidget = QWidget(self.container)
        self.layoutWidget.setObjectName(u"layoutWidget")
        self.layoutWidget.setGeometry(QRect(670, 260, 151, 171))
        self.verticalLayout = QVBoxLayout(self.layoutWidget)
        self.verticalLayout.setSpacing(60)
        self.verticalLayout.setObjectName(u"verticalLayout")
        self.verticalLayout.setSizeConstraint(QLayout.SetMaximumSize)
        self.verticalLayout.setContentsMargins(0, 0, 0, 0)
        self.chromeRadioButton = QRadioButton(self.layoutWidget)
        self.buttonGroup = QButtonGroup(MainWindow)
        self.buttonGroup.setObjectName(u"buttonGroup")
        self.buttonGroup.addButton(self.chromeRadioButton)
        self.chromeRadioButton.setObjectName(u"chromeRadioButton")
        font1 = QFont()
        font1.setPointSize(18)
        self.chromeRadioButton.setFont(font1)
        self.chromeRadioButton.setCursor(QCursor(Qt.PointingHandCursor))
        self.chromeRadioButton.setChecked(True)

        self.verticalLayout.addWidget(self.chromeRadioButton)

        self.edgeRadioButton = QRadioButton(self.layoutWidget)
        self.buttonGroup.addButton(self.edgeRadioButton)
        self.edgeRadioButton.setObjectName(u"edgeRadioButton")
        self.edgeRadioButton.setFont(font1)
        self.edgeRadioButton.setCursor(QCursor(Qt.PointingHandCursor))

        self.verticalLayout.addWidget(self.edgeRadioButton)

        self.label = QLabel(self.container)
        self.label.setObjectName(u"label")
        self.label.setGeometry(QRect(520, 180, 471, 41))
        font2 = QFont()
        font2.setPointSize(36)
        font2.setBold(False)
        self.label.setFont(font2)
        MainWindow.setCentralWidget(self.container)
        self.menubar = QMenuBar(MainWindow)
        self.menubar.setObjectName(u"menubar")
        self.menubar.setGeometry(QRect(0, 0, 1438, 24))
        MainWindow.setMenuBar(self.menubar)
        self.statusbar = QStatusBar(MainWindow)
        self.statusbar.setObjectName(u"statusbar")
        MainWindow.setStatusBar(self.statusbar)

        self.retranslateUi(MainWindow)

        QMetaObject.connectSlotsByName(MainWindow)
    # setupUi

    def retranslateUi(self, MainWindow):
        MainWindow.setWindowTitle(QCoreApplication.translate("MainWindow", u"MainWindow", None))
        self.pushButton.setText(QCoreApplication.translate("MainWindow", u"\u81ea\u52a8\u62a5\u9001", None))
        self.chromeRadioButton.setText(QCoreApplication.translate("MainWindow", u"Chrome", None))
        self.chromeRadioButton.setProperty("value", QCoreApplication.translate("MainWindow", u"chrome", None))
        self.edgeRadioButton.setText(QCoreApplication.translate("MainWindow", u"Edge", None))
        self.edgeRadioButton.setProperty("value", QCoreApplication.translate("MainWindow", u"msedge", None))
        self.label.setText(QCoreApplication.translate("MainWindow", u"\u8bf7\u9009\u62e9\u60a8\u7535\u8111\u652f\u6301\u7684\u6d4f\u89c8\u5668\uff1a", None))
    # retranslateUi

