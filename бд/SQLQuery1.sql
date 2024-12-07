USE DemoRequest
CREATE TABLE Users(
UserID integer NOT NULL PRIMARY KEY IDENTITY(1,1),
Llogin varchar(20) NOT NULL,
Ppassword varchar(20) NOT NULL
)
CREATE TABLE Clients(
ClientID integer NOT NULL PRIMARY KEY IDENTITY(1,1),
FIO varchar(100) NOT NULL,
Phone varchar(12) NOT NULL,
UserID integer NOT NULL, 
FOREIGN KEY (UserID) REFERENCES Users(UserID)
)
CREATE TABLE Masters(
MasterID integer NOT NULL PRIMARY KEY IDENTITY(1,1),
FIO varchar(100) NOT NULL,
Phone varchar(12) NOT NULL,
UserID integer NOT NULL, 
FOREIGN KEY (UserID) REFERENCES Users(UserID)
)
CREATE TABLE Managers(
ManagerID integer NOT NULL PRIMARY KEY IDENTITY(1,1),
FIO varchar(100) NOT NULL,
Phone varchar(12) NOT NULL,
UserID integer NOT NULL, 
FOREIGN KEY (UserID) REFERENCES Users(UserID)
)
CREATE TABLE Operators(
OperatorsID integer NOT NULL PRIMARY KEY IDENTITY(1,1),
FIO varchar(100) NOT NULL,
Phone varchar(12) NOT NULL,
UserID integer NOT NULL, 
FOREIGN KEY (UserID) REFERENCES Users(UserID)
)

CREATE TABLE HomeTechType(
HomeTechTypeID integer NOT NULL PRIMARY KEY IDENTITY(1,1),
Nname varchar(30) NOT NULL
)
CREATE TABLE HomeTechModel(
HomeTechModelID integer NOT NULL PRIMARY KEY IDENTITY(1,1),
Nname varchar(30) NOT NULL,
TypeID integer NOT NULL, 
FOREIGN KEY (TypeID) REFERENCES HomeTechType(HomeTechTypeID)
)
CREATE TABLE StatusRequests(
StatusRequestsID integer NOT NULL PRIMARY KEY IDENTITY(1,1),
Nname varchar(30) NOT NULL
)
CREATE TABLE Requests(
RequestID integer NOT NULL PRIMARY KEY IDENTITY(1,1),
StartDate datetime NOT NULL,
ModelID integer NOT NULL,
FOREIGN KEY (ModelID) REFERENCES HomeTechModel(HomeTechModelID),
ProblemDescription varchar(200) NOT NULL,
StatusRequest integer NOT NULL,
FOREIGN KEY (StatusRequest) REFERENCES StatusRequests(StatusRequestsID),
CompletionDate DATE,
RepairParts VARCHAR(36) NOT NULL,
MasterID INTEGER NOT NULL,
FOREIGN KEY (MasterID) REFERENCES Masters(MasterID),
ClientID INTEGER NOT NULL,
FOREIGN KEY (ClientID) REFERENCES Clients(ClientID)
)
CREATE TABLE Comments(
CommentID integer NOT NULL PRIMARY KEY IDENTITY(1,1),
Message varchar(200) NOT NULL,
MasterID integer NOT NULL,
RequestID integer NOT NULL,
FOREIGN KEY (MasterID) REFERENCES Masters(MasterID),
FOREIGN KEY (RequestID) REFERENCES Requests(RequestID )
)
CREATE TABLE EquipmentRequests(
EquipmentRequestID integer NOT NULL PRIMARY KEY IDENTITY(1,1),
ProblemDescription varchar(200),
Status integer NOT NULL,
FOREIGN KEY (Status) REFERENCES StatusRequests(StatusRequestsID),
RequestID integer NOT NULL,
FOREIGN KEY (RequestID) REFERENCES Requests(RequestID),
MasterID integer NOT NULL,
FOREIGN KEY (MasterID) REFERENCES Masters(MasterID),
ManagerID integer NOT NULL,
FOREIGN KEY (ManagerID) REFERENCES Managers(ManagerID)
)

USE DemoRequest

INSERT INTO Users (Llogin, Ppassword) 
VALUES ('kasoouser1', 'root'), 
       ('murashov123', 'qwerty'),
	   ('test1', 'test1'),
	   ('perinaAD', '250519'),
	   ('krutiha1234567', '1234567890'),
	   ('login1', 'pass1'),
	   ('login2', 'pass2'),
	   ('login3', 'pass3'),
	   ('login4', 'pass4'),
	   ('login5', 'pass5'),
	   	   ('client', '1111')


USE DemoRequest
INSERT INTO Clients (FIO, Phone, UserID) 
VALUES ('Иванов Иван Иванович', '1234567890', 11),
('Сидоров Владимир Владимирович', '89833378903', 11),
('Никитин Иван Петрович', '89876545674', 11),
('Алексеев Роман Ильич', '89034563234', 11),
('Петров Петр Петрович', '78-86-09', 11);

INSERT INTO Masters (FIO, Phone, UserID) 
VALUES ('Мурашов Андрей Юрьевич', '89535078985', 2),
('Степанов Андрей Викторович', '89210673849', 3),
('Семенова Ясмина Марковна', '89994563847', 4),
('Иванов Марк Максимович', '89994563844', 5)

INSERT INTO Managers (FIO, Phone, UserID) 
VALUES ('Трубин Никита Юрьевич', '89210563128', 1)

INSERT INTO Operators (FIO, Phone, UserID)  
VALUES ('Перина Анастасия Денисовна', '89990563748', 6),
('Мажитова Ксения Сергеевна', '89994563847', 7)


INSERT INTO HomeTechType (Nname) 
VALUES ('Фен'),
('Тостер'),
('Холодильник'),
('Стиральная машина'),
('Мультиварка');

INSERT INTO HomeTechModel ( Nname, TypeID) 
VALUES ('Ладомир ТА112 белый', 1),
('Redmond RT-437 черный', 2),
('Indesit DS 316 W белый', 3),
('DEXP WM-F610NTMA/WW белый', 4),
('Redmond RMC-M95 черный', 5),
('Ладомир ТА113 чёрный', 1);


INSERT INTO StatusRequests ( Nname) 
VALUES ('В процессе ремонта'),
('Готова к выдаче'),
('Новая заявка');

USE DemoRequest
INSERT INTO Requests (StartDate, ModelID, ProblemDescription, StatusRequest, CompletionDate, RepairParts, MasterID, ClientID)
VALUES ('2023-06-06', 1, 'Перестал работать', 1, NULL, 'Запчасть 1', 1, 1),
('2023-05-05', 2, 'Перестал работать', 2, NULL, 'Запчасть 13', 2, 2),
( '2022-07-07', 3, 'Не морозит одна из камер холодильника', 1, '2023-01-01', 'Запчасть 8', 2, 3),
('2023-08-02', 4, 'Перестали работать многие режимы стирки', 3, NULL, 'Запчасть 90', 1, 4),
('2023-08-02', 5, 'Перестала включаться', 1, NULL, 'Запчасть 22', 4, 5),
('2023-08-02', 6, 'Перестал работать', 2, '2023-08-03', 'Запчасть 1', 4, 3);

USE DemoRequest
INSERT INTO Comments (Message, MasterID, RequestID) 
VALUES ( 'Интересная поломка', 1, 7),
('Очень странно, будем разбираться!', 2, 8),
( 'Скорее всего потребуется мотор обдува!', 1, 10),
('Интересная проблема', 2, 9),
('Очень странно, будем разбираться!', 4, 11);

INSERT INTO EquipmentRequests ( ProblemDescription, Status, RequestID, MasterID, ManagerID) 
VALUES ('Нет нужного оборудования', 1, 7, 1, 1)



SELECT * FROM Comments 