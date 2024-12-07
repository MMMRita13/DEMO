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
VALUES ('������ ���� ��������', '1234567890', 11),
('������� �������� ������������', '89833378903', 11),
('������� ���� ��������', '89876545674', 11),
('�������� ����� �����', '89034563234', 11),
('������ ���� ��������', '78-86-09', 11);

INSERT INTO Masters (FIO, Phone, UserID) 
VALUES ('������� ������ �������', '89535078985', 2),
('�������� ������ ����������', '89210673849', 3),
('�������� ������ ��������', '89994563847', 4),
('������ ���� ����������', '89994563844', 5)

INSERT INTO Managers (FIO, Phone, UserID) 
VALUES ('������ ������ �������', '89210563128', 1)

INSERT INTO Operators (FIO, Phone, UserID)  
VALUES ('������ ��������� ���������', '89990563748', 6),
('�������� ������ ���������', '89994563847', 7)


INSERT INTO HomeTechType (Nname) 
VALUES ('���'),
('������'),
('�����������'),
('���������� ������'),
('�����������');

INSERT INTO HomeTechModel ( Nname, TypeID) 
VALUES ('������� ��112 �����', 1),
('Redmond RT-437 ������', 2),
('Indesit DS 316 W �����', 3),
('DEXP WM-F610NTMA/WW �����', 4),
('Redmond RMC-M95 ������', 5),
('������� ��113 ������', 1);


INSERT INTO StatusRequests ( Nname) 
VALUES ('� �������� �������'),
('������ � ������'),
('����� ������');

USE DemoRequest
INSERT INTO Requests (StartDate, ModelID, ProblemDescription, StatusRequest, CompletionDate, RepairParts, MasterID, ClientID)
VALUES ('2023-06-06', 1, '�������� ��������', 1, NULL, '�������� 1', 1, 1),
('2023-05-05', 2, '�������� ��������', 2, NULL, '�������� 13', 2, 2),
( '2022-07-07', 3, '�� ������� ���� �� ����� ������������', 1, '2023-01-01', '�������� 8', 2, 3),
('2023-08-02', 4, '��������� �������� ������ ������ ������', 3, NULL, '�������� 90', 1, 4),
('2023-08-02', 5, '��������� ����������', 1, NULL, '�������� 22', 4, 5),
('2023-08-02', 6, '�������� ��������', 2, '2023-08-03', '�������� 1', 4, 3);

USE DemoRequest
INSERT INTO Comments (Message, MasterID, RequestID) 
VALUES ( '���������� �������', 1, 7),
('����� �������, ����� �����������!', 2, 8),
( '������ ����� ����������� ����� ������!', 1, 10),
('���������� ��������', 2, 9),
('����� �������, ����� �����������!', 4, 11);

INSERT INTO EquipmentRequests ( ProblemDescription, Status, RequestID, MasterID, ManagerID) 
VALUES ('��� ������� ������������', 1, 7, 1, 1)



SELECT * FROM Comments 