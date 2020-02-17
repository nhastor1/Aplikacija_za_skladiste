BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "User" (
	"id"	INTEGER,
	"first_name"	TEXT,
	"last_name"	TEXT,
	"email"	TEXT,
	"username"	TEXT,
	"password"	TEXT,
	"admin"	INTEGER,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Language" (
    "lan" INTEGER,
    PRIMARY KEY("lan")
);
INSERT INTO "User" VALUES (1,'Admin', 'Admin', 'admin@admin', 'admin', 'Ad1!', 1);
INSERT INTO "Language" VALUES (1);
COMMIT;
