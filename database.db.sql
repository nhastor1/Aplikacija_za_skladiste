BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Product_order" (
	"id"	INTEGER,
	"product"	INTEGER,
	"amount"	INTEGER,
	"inovice"	INTEGER,
	"discount"	REAL,
	"timeOfOrder"	DATE,
	PRIMARY KEY("id"),
	FOREIGN KEY("product") REFERENCES "Product_order"("id"),
	FOREIGN KEY("inovice") REFERENCES "Inovice"("id")
);
CREATE TABLE IF NOT EXISTS "Inovice" (
	"id"	INTEGER,
	"customer"	INTEGER,
	"price"	REAL,
	"discount"	REAL,
	"timeOfOrder"	DATE,
	PRIMARY KEY("id"),
	FOREIGN KEY("customer") REFERENCES "Legal_person"("id")
);
CREATE TABLE IF NOT EXISTS "Product" (
	"id"	INTEGER,
	"name"	TEXT,
	"price"	REAL,
	"guarantee"	INTEGER,
	"category"	INTEGER,
	"manufacturer"	INTEGER,
	"lifetime"	DATE,
	"dateOfProduction"	DATE,
	"locationOfProduction"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("category") REFERENCES "Category"("id"),
	FOREIGN KEY("manufacturer") REFERENCES "Manufacturer"("id"),
	FOREIGN KEY("locationOfProduction") REFERENCES "Location"("id")
);
CREATE TABLE IF NOT EXISTS "Manufacturer" (
	"id"	INTEGER,
	"manufacturer"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("manufacturer") REFERENCES "Legal_person"("id")
);
CREATE TABLE IF NOT EXISTS "Category" (
	"id"	INTEGER,
	"name"	TEXT,
	"supercategory"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("supercategory") REFERENCES "Category"("id")
);
CREATE TABLE IF NOT EXISTS "Warehouse" (
	"id"	INTEGER,
	"name"	TEXT,
	"location"	INTEGER,
	"responsible_person"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("location") REFERENCES "Location"("id"),
	FOREIGN KEY("responsible_person") REFERENCES "Natural_person"("id")
);
CREATE TABLE IF NOT EXISTS "Legal_person" (
	"id"	INTEGER,
	"location"	INTEGER,
	"name"	TEXT,
	PRIMARY KEY("id"),
	FOREIGN KEY("location") REFERENCES "Location"("id")
);
CREATE TABLE IF NOT EXISTS "Natural_person" (
	"id"	INTEGER,
	"Location"	INTEGER,
	"name"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Location" (
	"id"	INTEGER,
	"street"	TEXT,
	"number"	INTEGER,
	"city"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("city") REFERENCES "City"("id")
);
CREATE TABLE IF NOT EXISTS "City" (
	"id"	INTEGER,
	"name"	TEXT,
	"country"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("country") REFERENCES "Country"("id")
);
CREATE TABLE IF NOT EXISTS "Country" (
	"id"	INTEGER,
	"name"	TEXT,
	"continent"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("continent") REFERENCES "Continent"("id")
);
CREATE TABLE IF NOT EXISTS "Continent" (
	"id"	INTEGER,
	"name"	TEXT,
	PRIMARY KEY("id")
);
INSERT INTO "Continent" VALUES (1,'Asia');
INSERT INTO "Continent" VALUES (2,'Afirca');
INSERT INTO "Continent" VALUES (3,'North America');
INSERT INTO "Continent" VALUES (4,'South America');
INSERT INTO "Continent" VALUES (5,'Europe');
INSERT INTO "Continent" VALUES (6,'Australia');
INSERT INTO "Continent" VALUES (7,'Antartica');
COMMIT;
