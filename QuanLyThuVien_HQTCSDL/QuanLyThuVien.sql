CREATE DATABASE QuanLyThuVien
USE QuanLyThuVien

CREATE TABLE DOCGIA
(
	ma_DocGia SMALLINT PRIMARY KEY,
	ho NVARCHAR(15) NOT NULL,
	tenlot NVARCHAR(1) NOT NULL,
	ten NVARCHAR(15) NOT NULL,
	ngaysinh SMALLDATETIME
)
GO

CREATE TABLE NGUOILON
(
	ma_DocGia SMALLINT PRIMARY KEY,
	sonha NVARCHAR(15) NOT NULL,
	duong NVARCHAR(63) NOT NULL,
	quan NVARCHAR(2) NOT NULL,
	dienthoai NVARCHAR(13) NULL,
	han_sd SMALLDATETIME NOT NULL
)
GO

CREATE TABLE TREEM
(
	ma_DocGia SMALLINT PRIMARY KEY,
	ma_DocGia_nguoilon SMALLINT NOT NULL
)
GO

CREATE TABLE TUASACH
(
	ma_tuasach INT PRIMARY KEY,
	tuasach NVARCHAR(63) NOT NULL,
	tacgia NVARCHAR(31) NOT NULL,
	tomtat NVARCHAR(222) NULL
)
GO

CREATE TABLE DAUSACH
(
	isbn INT PRIMARY KEY,
	ma_tuasach INT NOT NULL,
	ngonngu NVARCHAR(15),
	bia NVARCHAR(15),
	trangthai VARCHAR(1) NOT NULL
)
GO

CREATE TABLE CUONSACH
(
	isbn INT NOT NULL,
	ma_cuonsach SMALLINT NOT NULL,
	tinhtrang VARCHAR(1) NOT NULL,
	PRIMARY KEY(isbn, ma_cuonsach)
)
GO

CREATE TABLE DANGKY
(
	isbn INT NOT NULL,
	ma_DocGia SMALLINT NOT NULL,
	ngaygio_dk SMALLDATETIME,
	ghichu NVARCHAR(255),
	PRIMARY KEY (isbn, ma_DocGia)
)
GO

CREATE TABLE MUON
(
	isbn INT NOT NULL,
	ma_cuonsach SMALLINT NOT NULL,
	ma_DocGia SMALLINT NOT NULL,
	ngaygio_muon SMALLDATETIME NOT NULL,
	ngay_hethan SMALLDATETIME NOT NULL,
	PRIMARY KEY(isbn, ma_cuonsach)
)
GO

CREATE TABLE QUATRINHMUON
(
	isbn INT NOT NULL,
	ma_cuonsach SMALLINT,
	ngaygio_muon SMALLDATETIME NOT NULL,
	ma_DocGia SMALLINT NOT NULL,
	ngay_hethan SMALLDATETIME NULL,
	ngaygio_tra SMALLDATETIME NULL,
	tien_muon MONEY NULL,
	tien_datra MONEY NULL,
	tien_datcoc MONEY NULL,
	ghichu NVARCHAR(255) NULL,
	PRIMARY KEY(isbn, ma_cuonsach, ngaygio_muon)
)
GO
--Khoá ngoại
ALTER TABLE NGUOILON ADD CONSTRAINT FK_nguoilon_docgia FOREIGN KEY(ma_DocGia) REFERENCES DOCGIA(ma_DocGia)
GO
ALTER TABLE TREEM ADD CONSTRAINT FK_trem_nguoilon FOREIGN KEY(ma_DocGia_nguoilon) REFERENCES NGUOILON(ma_DocGia)
GO
ALTER TABLE TREEM ADD CONSTRAINT FK_treem_docgia FOREIGN KEY(ma_DocGia) REFERENCES DOCGIA(ma_DocGia)
GO
ALTER TABLE DAUSACH ADD CONSTRAINT FK_dausach_tuasach FOREIGN KEY(ma_tuasach) REFERENCES TUASACH(ma_tuasach)
GO
ALTER TABLE CUONSACH ADD CONSTRAINT FK_cuonsach_dausach FOREIGN KEY(isbn) REFERENCES DAUSACH(isbn)
GO
ALTER TABLE DANGKY ADD CONSTRAINT FK_dangky_docgia FOREIGN KEY(ma_DocGia) REFERENCES DOCGIA(ma_DocGia)
GO
ALTER TABLE DANGKY ADD CONSTRAINT FK_dangky_dausach FOREIGN KEY(isbn) REFERENCES DAUSACH(isbn)
GO
ALTER TABLE MUON ADD CONSTRAINT FK_muon_docgia FOREIGN KEY(ma_DocGia) REFERENCES DOCGIA(ma_DocGia)
GO
ALTER TABLE MUON ADD CONSTRAINT FK_muon_cuonsach FOREIGN KEY(isbn, ma_cuonsach) REFERENCES CUONSACH(isbn, ma_cuonsach)
GO
ALTER TABLE QUATRINHMUON ADD CONSTRAINT FK_quatrinhmuon_docgia FOREIGN KEY(ma_DocGia) REFERENCES DOCGIA(ma_DocGia)
GO
ALTER TABLE QUATRINHMUON ADD CONSTRAINT FK_quatrinhmuon_cuonsach FOREIGN KEY(isbn, ma_cuonsach) REFERENCES CUONSACH(isbn, ma_cuonsach)


--3. Bài tập 3
--t. Viết một stored proc có nội dung: Dùng lệnh print để in ra danh sách mã các tựa sách.
ALTER PROC USP_InMaTuaSach
@ma_tuasach INT OUT
AS
BEGIN
	SELECT @ma_tuasach = ma_tuasach
	FROM TUASACH

END

declare @ma_tuasach INT
EXEC USP_InMaTuaSach @ma_tuasach OUT
print @ma_tuasach
--u. Viết một stored proc có nội dung: Dùng lệnh print để in ra danh sách mã và họ tên các độc giả.

--4. Bài tập Stored Procedure
--4.1. Xem thông tin độc giả
ALTER PROC sp_ThongtinDocGia
@ma_DocGia INT
AS
BEGIN
	DECLARE @TUOI INT
	SET @TUOI = DATEDIFF (YY,(SELECT ngaysinh FROM DOCGIA WHERE ma_DocGia = @ma_DocGia ), GETDATE())
	PRINT @TUOI
	IF(@TUOI >= 18)
	BEGIN
		SELECT * 
		FROM DOCGIA, NGUOILON
		WHERE DOCGIA.ma_DocGia = NGUOILON.ma_DocGia AND DOCGIA.ma_DocGia = @ma_DocGia
	END

	ELSE
	BEGIN
		SELECT * 
		FROM DOCGIA, TREEM 
		WHERE DOCGIA.ma_DocGia = TREEM.ma_DocGia AND DOCGIA.ma_DocGia = @ma_DocGia
	END
END

EXEC sp_ThongtinDocGia 1

--4.2. Thông tin đầu sách
ALTER PROC sp_ThongtinDausach
@ISBN INT
AS
BEGIN
	SELECT DAUSACH.isbn, ma_tuasach, ngonngu, bia, trangthai, COUNT(*) AS [Số lượng sách chưa mượn]
	FROM DAUSACH, CUONSACH 
	WHERE DAUSACH.isbn = @ISBN AND DAUSACH.isbn = CUONSACH.isbn AND tinhtrang = 'Y'
	GROUP BY DAUSACH.isbn, ma_tuasach, ngonngu, bia, trangthai
END

EXEC sp_ThongtinDausach 5

--4.3. Liệt kê những độc giả người lớn đang mượn sách
--Khi chưa trả sách ngày trả sách là NULL, khi trả sách rồi thủ thư sẽ nhập ngày trả sách vào
ALTER PROC sp_ThongtinNguoilonDangmuon
AS
BEGIN
	SELECT DOCGIA.ma_DocGia, ho, tenlot, ten, ngaysinh, sonha, duong, quan, dienthoai, han_sd
	FROM DOCGIA, NGUOILON, (SELECT DISTINCT ma_DocGia
															FROM MUON 
															WHERE NOT EXISTS(SELECT isbn, ma_cuonsach, ngaygio_muon, ma_DocGia
																			FROM QUATRINHMUON
																			WHERE isbn = MUON.isbn AND ma_cuonsach = MUON.ma_cuonsach AND ma_DocGia = MUON.ma_DocGia AND ngaygio_muon = MUON.ngaygio_muon)) AS A
	WHERE DOCGIA.ma_DocGia = NGUOILON.ma_DocGia AND DOCGIA.ma_DocGia = A.ma_DocGia AND NGUOILON.ma_DocGia = A.ma_DocGia
END


SELECT DISTINCT MUON.ma_DocGia
FROM MUON, NGUOILON
WHERE NOT EXISTS(SELECT isbn, ma_cuonsach, ngaygio_muon, ma_DocGia
				FROM QUATRINHMUON 
				WHERE isbn = MUON.isbn AND ma_cuonsach = MUON.ma_cuonsach AND ma_DocGia = MUON.ma_DocGia AND ngaygio_muon = MUON.ngaygio_muon)
	AND MUON.ma_DocGia = NGUOILON.ma_DocGia
EXEC sp_ThongtinNguoilonDangmuon
INSERT INTO QUATRINHMUON VALUES('19', '5', '2008-08-13', '3', '2008-08-27', NULL, NULL, NULL, NULL, NULL)
--4.4. Liệt kê những độc giả người lớn đang mượn sách quá hạn
ALTER PROC sp_ThongtinNguoilonQuahan
AS
BEGIN
	SELECT DISTINCT DOCGIA.ma_DocGia, ho, tenlot, ten, ngaysinh
	FROM DOCGIA, MUON , NGUOILON
	WHERE DOCGIA.ma_DocGia = NGUOILON.ma_DocGia AND DOCGIA.ma_DocGia = MUON.ma_DocGia AND ngay_hethan < GETDATE() 
	AND DOCGIA.ma_DocGia IN (SELECT DISTINCT MUON.ma_DocGia
							FROM MUON, NGUOILON 
							WHERE NOT EXISTS(SELECT isbn, ma_cuonsach, ngaygio_muon, ma_DocGia
											FROM QUATRINHMUON 
											WHERE isbn = MUON.isbn AND ma_cuonsach = MUON.ma_cuonsach AND ma_DocGia = MUON.ma_DocGia AND ngaygio_muon = MUON.ngaygio_muon)
													AND MUON.ma_DocGia = NGUOILON.ma_DocGia)
END

EXEC sp_ThongtinNguoilonQuahan

--4.5. Liệt kê những độc giả người lớn đang mượn sách có trẻ em cũng đang mượn sách

ALTER PROC sp_DocGiaCoTreEmMuon
AS 
BEGIN
	SELECT DISTINCT A.ma_DocGia, ho, tenlot, ten, ngaysinh
	FROM DOCGIA , 
		 (SELECT DISTINCT MUON.ma_DocGia
		  FROM MUON, NGUOILON 
		  WHERE NOT EXISTS(SELECT isbn, ma_cuonsach, ngaygio_muon, ma_DocGia
						   FROM QUATRINHMUON 
							WHERE isbn = MUON.isbn AND ma_cuonsach = MUON.ma_cuonsach AND ma_DocGia = MUON.ma_DocGia AND ngaygio_muon = MUON.ngaygio_muon)
								AND MUON.ma_DocGia = NGUOILON.ma_DocGia) AS A,
		 (SELECT DISTINCT TREEM.ma_DocGia_nguoilon
		  FROM MUON, TREEM 
		  WHERE NOT EXISTS(SELECT isbn, ma_cuonsach, ngaygio_muon, ma_DocGia
						   FROM QUATRINHMUON 
						   WHERE isbn = MUON.isbn AND ma_cuonsach = MUON.ma_cuonsach AND ma_DocGia = MUON.ma_DocGia AND ngaygio_muon = MUON.ngaygio_muon)
							AND MUON.ma_DocGia = TREEM.ma_DocGia) AS B
	WHERE DOCGIA.ma_DocGia = A.ma_DocGia AND A.ma_DocGia = B.ma_DocGia_nguoilon
END



EXEC sp_DocGiaCoTreEmMuon
insert into Muon values (29, 5, 4, '8/4/2008', '8/18/2008')
INSERT INTO QUATRINHMUON VALUES(29, 5, '8/4/2008', 4, 8/18/2008, NULL, NULL, NULL, NULL, NULL)
--4.6. Cập nhật trạng thái của đầu sách
GO
ALTER PROC sp_CapnhatTrangthaiDausach
@ISBN INT
AS
BEGIN
	DECLARE @SLSachChuaMuon INT, @trangthai VARCHAR(1)

	SELECT @trangthai = trangthai
	FROM DAUSACH
	WHERE isbn = @ISBN

	SELECT @SLSachChuaMuon = COUNT(*) 
	FROM DAUSACH, CUONSACH
	WHERE DAUSACH.isbn = CUONSACH.isbn AND DAUSACH.isbn = @ISBN AND tinhtrang = 'Y'

	IF(@SLSachChuaMuon > 0 AND @trangthai = 'N')
	BEGIN
		UPDATE DAUSACH
		SET trangthai = 'Y'
		WHERE isbn = @ISBN
	END
	ELSE IF(@SLSachChuaMuon = 0 AND @trangthai = 'Y')
	BEGIN
		UPDATE DAUSACH
		SET trangthai = 'N'
		WHERE isbn = @ISBN
	END
END

EXEC sp_CapnhatTrangthaiDausach 4

--4.7. Thêm tựa sách mới
GO
ALTER PROC sp_ThemTuaSach
@tuasach NVARCHAR(63), @tacgia NVARCHAR(31), @tomtat NVARCHAR(222)
AS
BEGIN
	DECLARE @I INT, @MAX_MATUASACH INT

	SELECT @MAX_MATUASACH = MAX(ma_tuasach)
	FROM TUASACH
	SET @I = 1

	IF((SELECT COUNT(*) FROM TUASACH  WHERE tuasach = @tuasach) > 0 AND
		(SELECT COUNT(*) FROM TUASACH  WHERE tacgia = @tacgia) > 0 AND
		((SELECT COUNT(*) FROM TUASACH  WHERE tomtat = @tomtat) > 0 OR(@tomtat IS NULL AND (SELECT COUNT(*) FROM TUASACH WHERE tomtat IS NULL) > 0)))
	BEGIN
		PRINT N'Phải có ít nhất 1 trong 3 thuộc tính tựa sách, tác giả, tóm tắt khác với các bộ trong bảng tựa sách đã có'
	END
	ELSE
	BEGIN
		WHILE(@I <= @MAX_MATUASACH + 1)
		BEGIN
			IF((SELECT COUNT(*) FROM TUASACH  WHERE ma_tuasach = @I) < 1)
			BEGIN
				waitfor delay '00:00:10'
				INSERT INTO TUASACH VALUES(@I, @tuasach, @tacgia, @tomtat)
				BREAK
			END

			SET @I += 1
		END
	END
END

delete from TUASACH where ma_tuasach = 56
--4.8. Thêm cuốn sách mới
GO
CREATE PROC sp_ThemCuonSach
@ISBN INT
AS
BEGIN
	DECLARE @I INT, @MAX_MACUONSACH SMALLINT

	SELECT @MAX_MACUONSACH = MAX(ma_cuonsach)
	FROM CUONSACH

	IF((SELECT COUNT(*) FROM DAUSACH WHERE isbn = @ISBN) < 1)
	BEGIN
		PRINT N'Mã isbn không tồn tại'
		RETURN 
	END

	ELSE
	BEGIN
		IF(@MAX_MACUONSACH IS NULL)
		BEGIN
				INSERT INTO CUONSACH VALUES(@ISBN, 1, 'Y')
				UPDATE DAUSACH SET trangthai = 'Y' WHERE isbn = @ISBN
		END
		ELSE
		BEGIN

		SET @I = 1

		WHILE(@I <= @MAX_MACUONSACH + 1)
		BEGIN
			IF((SELECT COUNT(*) FROM CUONSACH WHERE ma_cuonsach = @I AND isbn = @ISBN) < 1)
			BEGIN
				INSERT INTO CUONSACH VALUES(@ISBN, @I, 'Y')
				UPDATE DAUSACH SET trangthai = 'Y' WHERE isbn = @ISBN
			END
			SET @I += 1
		END
		END
	END
END
					
INSERT INTO CUONSACH VALUES(1, 7, 'Y')
SELECT * FROM DAUSACH
SELECT * FROM CUONSACH

--4.9. Thêm độc giả người lớn
GO
CREATE PROC sp_ThemNguoilon
@HO NVARCHAR(15), @TENLOT  NVARCHAR(1), @TEN NVARCHAR(15), @NGAYSINH SMALLDATETIME, 
@SONHA NVARCHAR(15), @DUONG NVARCHAR(63), @QUAN NVARCHAR(2), @DIENTHOAI NVARCHAR(13)
AS
BEGIN
	DECLARE @MAX_MADOCGIA SMALLINT, @I INT, @MADOCGIA SMALLINT, @HANSD SMALLDATETIME

	SELECT @MAX_MADOCGIA = MAX(ma_DocGia)
	FROM DOCGIA

	SET @I = 1

	WHILE(@I <= @MAX_MADOCGIA + 1)
	BEGIN
		IF((SELECT COUNT(*) FROM DOCGIA WHERE ma_DocGia = @I) < 1)
		BEGIN
			INSERT INTO DOCGIA VALUES(@I, @HO, @TENLOT, @TEN, @NGAYSINH)
			SET @MADOCGIA = @I
			BREAK
		END

		SET @I += 1
	END
	
	IF((DATEDIFF(YYYY, @NGAYSINH, GETDATE())) < 18)
	BEGIN
		PRINT N'Độc giả chưa đủ 18 tuổi'
		RETURN
	END
	ELSE
	BEGIN
		SET @HANSD = DATEADD(YEAR,1,GETDATE())
		INSERT INTO NGUOILON VALUES(@MADOCGIA, @SONHA, @DUONG, @QUAN, @DIENTHOAI, @HANSD)
	END
END

EXEC sp_ThemNguoilon N'Nguyễn', N'T', N'Lan', '1/1/1999', '1', N'Phạm Văn Đồng', N'Quận 9', '0962456190'

--4.10. Thêm độc giả trẻ em
GO
CREATE PROC sp_ThemTreEm
@HO NVARCHAR(15), @TENLOT NVARCHAR(1), @TEN NVARCHAR(15), @NGAYSINH SMALLDATETIME, @MA_DOCGIA_NGUOILON SMALLINT
AS
BEGIN
	DECLARE @MAX_MADOCGIA SMALLINT, @I INT, @MADOCGIA SMALLINT, @MAX_MADOCGIA_TE SMALLINT

	SELECT @MAX_MADOCGIA = MAX(ma_DocGia)
	FROM DOCGIA

	SET @I = 1

	IF(@MAX_MADOCGIA IS NULL)
	BEGIN
		INSERT INTO DOCGIA VALUES(1, @HO, @TENLOT, @TEN, @NGAYSINH)
	END
	ELSE
	BEGIN
		WHILE(@I <= @MAX_MADOCGIA + 1)
		BEGIN
			IF((SELECT COUNT(*) FROM DOCGIA WHERE ma_DocGia = @I) < 1)
			BEGIN
				INSERT INTO DOCGIA VALUES(@I, @HO, @TENLOT, @TEN, @NGAYSINH)
				SET @MADOCGIA = @I
				BREAK
			END
			SET @I += 1
		END
	END

	IF((SELECT COUNT(*) FROM TREEM WHERE ma_DocGia_nguoilon = @MA_DOCGIA_NGUOILON GROUP BY ma_DocGia_nguoilon) = 2)
	BEGIN
		PRINT N'Lỗi, mỗi độc giả người lớn chỉ bảo lãnh tối đa cho 2 trẻ em'
		RETURN
	END
	ELSE
	BEGIN
		SET @I = 1

		SELECT @MAX_MADOCGIA_TE = MAX(ma_DocGia)
		FROM TREEM

		IF(@MAX_MADOCGIA_TE IS NULL)
		BEGIN 
			INSERT INTO TREEM VALUES(1, @MA_DOCGIA_NGUOILON)
		END
		ELSE
		BEGIN
			WHILE(@I <= @MAX_MADOCGIA_TE + 1)
			BEGIN
				IF((SELECT COUNT(*) FROM TREEM WHERE ma_DocGia = @I) < 1)
				BEGIN
					INSERT INTO TREEM VALUES(@I, @MA_DOCGIA_NGUOILON)
					BREAK
				END
				SET @I += 1
			END
		END
	END
END

--4.11. Xóa độc giả
GO
ALTER PROC sp_XoaDocGia
@ma_DocGia INT
AS
BEGIN
--[1]
	IF((SELECT COUNT(*) FROM DOCGIA WHERE ma_DocGia = @ma_DocGia) < 1)
	BEGIN
		PRINT N'Không tồn tại độc giả'
		RETURN
	END
--[2]
	IF((SELECT COUNT(*)
		FROM DOCGIA, QUATRINHMUON
		WHERE DOCGIA.ma_DocGia = QUATRINHMUON.ma_DocGia AND ngaygio_tra IS NULL AND DOCGIA.ma_DocGia = @ma_DocGia
		GROUP BY DOCGIA.ma_DocGia) >= 1)
	BEGIN
		PRINT N'Không thể xóa độc giả được'
		RETURN
	END
--[3]
	IF(@ma_DocGia IN(SELECT ma_DocGia FROM NGUOILON))
	BEGIN
		IF(@ma_DocGia NOT IN(SELECT ma_DocGia_nguoilon FROM TREEM))
		BEGIN
			DELETE FROM NGUOILON WHERE ma_DocGia = @ma_DocGia
			DELETE FROM QUATRINHMUON WHERE ma_DocGia = @ma_DocGia
			DELETE FROM MUON WHERE ma_DocGia = @ma_DocGia
			DELETE FROM DANGKY WHERE ma_DocGia = @ma_DocGia
			DELETE FROM DOCGIA WHERE ma_DocGia = @ma_DocGia
		END
		
		ELSE
		BEGIN
			DECLARE @MATREEM SMALLINT
			SELECT @MATREEM = ma_DocGia
			FROM TREEM
			WHERE ma_DocGia_nguoilon = @ma_DocGia

			DELETE FROM TREEM WHERE ma_DocGia = @MATREEM
			DELETE FROM QUATRINHMUON WHERE ma_DocGia = @MATREEM
			DELETE FROM MUON WHERE ma_DocGia = @MATREEM
			DELETE FROM DANGKY WHERE ma_DocGia = @MATREEM
			DELETE FROM DOCGIA WHERE ma_DocGia = @MATREEM

			DELETE FROM NGUOILON WHERE ma_DocGia = @ma_DocGia
			DELETE FROM QUATRINHMUON WHERE ma_DocGia = @ma_DocGia
			DELETE FROM MUON WHERE ma_DocGia = @ma_DocGia
			DELETE FROM DANGKY WHERE ma_DocGia = @ma_DocGia
			DELETE FROM DOCGIA WHERE ma_DocGia = @ma_DocGia
		END
	END

	ELSE
	BEGIN
		DELETE FROM TREEM WHERE ma_DocGia = @ma_DocGia
		DELETE FROM QUATRINHMUON WHERE ma_DocGia = @ma_DocGia
		DELETE FROM MUON WHERE ma_DocGia = @ma_DocGia
		DELETE FROM DANGKY WHERE ma_DocGia = @ma_DocGia
		DELETE FROM DOCGIA WHERE ma_DocGia = @ma_DocGia
	END
END



