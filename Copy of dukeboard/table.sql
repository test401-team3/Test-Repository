DROP SEQUENCE board_num_seq;
DROP TABLE board;

CREATE TABLE board (
	num INTEGER NOT NULL,
	writer VARCHAR(60) NOT NULL,
	title VARCHAR(300) NOT NULL,
	contents CLOB NOT NULL,
	ip VARCHAR(39),
	read_count INTEGER DEFAULT 0,
	reg_date DATE NOT NULL,
	mod_date DATE,
	PRIMARY KEY (num)
);

CREATE SEQUENCE board_num_seq START with 1 INCREMENT BY 1;

COMMIT;
