using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using pz19.Models;

namespace pz19;

public partial class DemoRequestContext : DbContext
{
    public DemoRequestContext()
    {
    }

    public DemoRequestContext(DbContextOptions<DemoRequestContext> options)
        : base(options)
    {
    }

    public virtual DbSet<Client> Clients { get; set; }

    public virtual DbSet<Comment> Comments { get; set; }

    public virtual DbSet<EquipmentRequest> EquipmentRequests { get; set; }

    public virtual DbSet<HomeTechModel> HomeTechModels { get; set; }

    public virtual DbSet<HomeTechType> HomeTechTypes { get; set; }

    public virtual DbSet<Manager> Managers { get; set; }

    public virtual DbSet<Master> Masters { get; set; }

    public virtual DbSet<Operator> Operators { get; set; }

    public virtual DbSet<Request> Requests { get; set; }

    public virtual DbSet<StatusRequest> StatusRequests { get; set; }

    public virtual DbSet<User> Users { get; set; }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. You can avoid scaffolding the connection string by using the Name= syntax to read it from configuration - see https://go.microsoft.com/fwlink/?linkid=2131148. For more guidance on storing connection strings, see https://go.microsoft.com/fwlink/?LinkId=723263.
        => optionsBuilder.UseSqlServer("Server=localhost;DataBase=DemoRequest;Trusted_Connection=True;TrustServerCertificate=True;");

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Client>(entity =>
        {
            entity.HasKey(e => e.ClientId).HasName("PK__Clients__E67E1A043CE28E58");

            entity.Property(e => e.ClientId).HasColumnName("ClientID");
            entity.Property(e => e.Fio)
                .HasMaxLength(100)
                .IsUnicode(false)
                .HasColumnName("FIO");
            entity.Property(e => e.Phone)
                .HasMaxLength(12)
                .IsUnicode(false);
            entity.Property(e => e.UserId).HasColumnName("UserID");

            entity.HasOne(d => d.User).WithMany(p => p.Clients)
                .HasForeignKey(d => d.UserId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK__Clients__UserID__398D8EEE");
        });

        modelBuilder.Entity<Comment>(entity =>
        {
            entity.HasKey(e => e.CommentId).HasName("PK__Comments__C3B4DFAAB5FC9889");

            entity.Property(e => e.CommentId).HasColumnName("CommentID");
            entity.Property(e => e.MasterId).HasColumnName("MasterID");
            entity.Property(e => e.Message)
                .HasMaxLength(200)
                .IsUnicode(false);
            entity.Property(e => e.RequestId).HasColumnName("RequestID");

            entity.HasOne(d => d.Master).WithMany(p => p.Comments)
                .HasForeignKey(d => d.MasterId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK__Comments__Master__5165187F");

            entity.HasOne(d => d.Request).WithMany(p => p.Comments)
                .HasForeignKey(d => d.RequestId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK__Comments__Reques__52593CB8");
        });

        modelBuilder.Entity<EquipmentRequest>(entity =>
        {
            entity.HasKey(e => e.EquipmentRequestId).HasName("PK__Equipmen__B3EA0A0957ADD0FC");

            entity.Property(e => e.EquipmentRequestId).HasColumnName("EquipmentRequestID");
            entity.Property(e => e.ManagerId).HasColumnName("ManagerID");
            entity.Property(e => e.MasterId).HasColumnName("MasterID");
            entity.Property(e => e.ProblemDescription)
                .HasMaxLength(200)
                .IsUnicode(false);
            entity.Property(e => e.RequestId).HasColumnName("RequestID");

            entity.HasOne(d => d.Manager).WithMany(p => p.EquipmentRequests)
                .HasForeignKey(d => d.ManagerId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK__Equipment__Manag__5812160E");

            entity.HasOne(d => d.Master).WithMany(p => p.EquipmentRequests)
                .HasForeignKey(d => d.MasterId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK__Equipment__Maste__571DF1D5");

            entity.HasOne(d => d.Request).WithMany(p => p.EquipmentRequests)
                .HasForeignKey(d => d.RequestId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK__Equipment__Reque__5629CD9C");

            entity.HasOne(d => d.StatusNavigation).WithMany(p => p.EquipmentRequests)
                .HasForeignKey(d => d.Status)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK__Equipment__Statu__5535A963");
        });

        modelBuilder.Entity<HomeTechModel>(entity =>
        {
            entity.HasKey(e => e.HomeTechModelId).HasName("PK__HomeTech__E5C076B46FC53A65");

            entity.ToTable("HomeTechModel");

            entity.Property(e => e.HomeTechModelId).HasColumnName("HomeTechModelID");
            entity.Property(e => e.Nname)
                .HasMaxLength(30)
                .IsUnicode(false);
            entity.Property(e => e.TypeId).HasColumnName("TypeID");

            entity.HasOne(d => d.Type).WithMany(p => p.HomeTechModels)
                .HasForeignKey(d => d.TypeId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK__HomeTechM__TypeI__46E78A0C");
        });

        modelBuilder.Entity<HomeTechType>(entity =>
        {
            entity.HasKey(e => e.HomeTechTypeId).HasName("PK__HomeTech__AC20314FB8452F9C");

            entity.ToTable("HomeTechType");

            entity.Property(e => e.HomeTechTypeId).HasColumnName("HomeTechTypeID");
            entity.Property(e => e.Nname)
                .HasMaxLength(30)
                .IsUnicode(false);
        });

        modelBuilder.Entity<Manager>(entity =>
        {
            entity.HasKey(e => e.ManagerId).HasName("PK__Managers__3BA2AA81ABA2D582");

            entity.Property(e => e.ManagerId).HasColumnName("ManagerID");
            entity.Property(e => e.Fio)
                .HasMaxLength(100)
                .IsUnicode(false)
                .HasColumnName("FIO");
            entity.Property(e => e.Phone)
                .HasMaxLength(12)
                .IsUnicode(false);
            entity.Property(e => e.UserId).HasColumnName("UserID");

            entity.HasOne(d => d.User).WithMany(p => p.Managers)
                .HasForeignKey(d => d.UserId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK__Managers__UserID__3F466844");
        });

        modelBuilder.Entity<Master>(entity =>
        {
            entity.HasKey(e => e.MasterId).HasName("PK__Masters__F6B782C4F0CCAAD8");

            entity.Property(e => e.MasterId).HasColumnName("MasterID");
            entity.Property(e => e.Fio)
                .HasMaxLength(100)
                .IsUnicode(false)
                .HasColumnName("FIO");
            entity.Property(e => e.Phone)
                .HasMaxLength(12)
                .IsUnicode(false);
            entity.Property(e => e.UserId).HasColumnName("UserID");

            entity.HasOne(d => d.User).WithMany(p => p.Masters)
                .HasForeignKey(d => d.UserId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK__Masters__UserID__3C69FB99");
        });

        modelBuilder.Entity<Operator>(entity =>
        {
            entity.HasKey(e => e.OperatorsId).HasName("PK__Operator__FE46C8D9FEB09181");

            entity.Property(e => e.OperatorsId).HasColumnName("OperatorsID");
            entity.Property(e => e.Fio)
                .HasMaxLength(100)
                .IsUnicode(false)
                .HasColumnName("FIO");
            entity.Property(e => e.Phone)
                .HasMaxLength(12)
                .IsUnicode(false);
            entity.Property(e => e.UserId).HasColumnName("UserID");

            entity.HasOne(d => d.User).WithMany(p => p.Operators)
                .HasForeignKey(d => d.UserId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK__Operators__UserI__4222D4EF");
        });

        modelBuilder.Entity<Request>(entity =>
        {
            entity.HasKey(e => e.RequestId).HasName("PK__Requests__33A8519AC620A65D");

            entity.Property(e => e.RequestId).HasColumnName("RequestID");
            entity.Property(e => e.ClientId).HasColumnName("ClientID");
            entity.Property(e => e.MasterId).HasColumnName("MasterID");
            entity.Property(e => e.ModelId).HasColumnName("ModelID");
            entity.Property(e => e.ProblemDescription)
                .HasMaxLength(200)
                .IsUnicode(false);
            entity.Property(e => e.RepairParts)
                .HasMaxLength(36)
                .IsUnicode(false);
            entity.Property(e => e.StartDate).HasColumnType("datetime");

            entity.HasOne(d => d.Client).WithMany(p => p.Requests)
                .HasForeignKey(d => d.ClientId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK__Requests__Client__4E88ABD4");

            entity.HasOne(d => d.Master).WithMany(p => p.Requests)
                .HasForeignKey(d => d.MasterId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK__Requests__Master__4D94879B");

            entity.HasOne(d => d.Model).WithMany(p => p.Requests)
                .HasForeignKey(d => d.ModelId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK__Requests__ModelI__4BAC3F29");

            entity.HasOne(d => d.StatusRequestNavigation).WithMany(p => p.Requests)
                .HasForeignKey(d => d.StatusRequest)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK__Requests__Status__4CA06362");
        });

        modelBuilder.Entity<StatusRequest>(entity =>
        {
            entity.HasKey(e => e.StatusRequestsId).HasName("PK__StatusRe__D54506BCAAFAC7C0");

            entity.Property(e => e.StatusRequestsId).HasColumnName("StatusRequestsID");
            entity.Property(e => e.Nname)
                .HasMaxLength(30)
                .IsUnicode(false);
        });

        modelBuilder.Entity<User>(entity =>
        {
            entity.HasKey(e => e.UserId).HasName("PK__Users__1788CCAC267E9053");

            entity.Property(e => e.UserId).HasColumnName("UserID");
            entity.Property(e => e.Llogin)
                .HasMaxLength(20)
                .IsUnicode(false);
            entity.Property(e => e.Ppassword)
                .HasMaxLength(20)
                .IsUnicode(false);
        });

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
