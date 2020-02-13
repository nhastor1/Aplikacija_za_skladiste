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
INSERT INTO "User" VALUES (1,'Admin', 'Admin', 'admin@admin', 'admin', 'Admin123!', 1);
COMMIT;
