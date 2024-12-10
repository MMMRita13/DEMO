using System;
using System.Collections.Generic;

namespace pz19.Models;

public partial class Master
{
    public int MasterId { get; set; }

    public string Fio { get; set; } = null!;

    public string Phone { get; set; } = null!;

    public int UserId { get; set; }

    public virtual ICollection<Comment> Comments { get; set; } = new List<Comment>();

    public virtual ICollection<EquipmentRequest> EquipmentRequests { get; set; } = new List<EquipmentRequest>();

    public virtual ICollection<Request> Requests { get; set; } = new List<Request>();

    public virtual User User { get; set; } = null!;
}
