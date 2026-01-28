CREATE TABLE PRICES (
                        BRAND_ID INT NOT NULL,
                        START_DATE TIMESTAMP NOT NULL,
                        END_DATE TIMESTAMP NOT NULL,
                        PRICE_LIST INT NOT NULL,
                        PRODUCT_ID INT NOT NULL,
                        PRIORITY INT NOT NULL,
                        PRICE DECIMAL(10,2) NOT NULL,
                        CURR CHAR(3) NOT NULL,
                        PRIMARY KEY (BRAND_ID, PRICE_LIST, PRODUCT_ID, START_DATE)
);
