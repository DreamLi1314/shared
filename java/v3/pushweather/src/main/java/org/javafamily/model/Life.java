package org.javafamily.model;

import java.io.Serializable;

public class Life implements Serializable {
    private String date;
    private Info info;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    class Info implements Serializable {
        private String[] kongtiao;
        private String[] yundong;
        private String[] ziwaixian;
        private String[] ganmao;
        private String[] xiche;
        private String[] wuran;
        private String[] chuanyi;

        public String[] getKongtiao() {
            return kongtiao;
        }

        public void setKongtiao(String[] kongtiao) {
            this.kongtiao = kongtiao;
        }

        public String[] getYundong() {
            return yundong;
        }

        public void setYundong(String[] yundong) {
            this.yundong = yundong;
        }

        public String[] getZiwaixian() {
            return ziwaixian;
        }

        public void setZiwaixian(String[] ziwaixian) {
            this.ziwaixian = ziwaixian;
        }

        public String[] getGanmao() {
            return ganmao;
        }

        public void setGanmao(String[] ganmao) {
            this.ganmao = ganmao;
        }

        public String[] getXiche() {
            return xiche;
        }

        public void setXiche(String[] xiche) {
            this.xiche = xiche;
        }

        public String[] getWuran() {
            return wuran;
        }

        public void setWuran(String[] wuran) {
            this.wuran = wuran;
        }

        public String[] getChuanyi() {
            return chuanyi;
        }

        public void setChuanyi(String[] chuanyi) {
            this.chuanyi = chuanyi;
        }
    }
}
