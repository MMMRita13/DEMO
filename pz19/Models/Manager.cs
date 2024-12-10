using System;
using System.Collections.Generic;

namespace pz19.Models;

public partial class Manager
{
    public int ManagerId { get; set; }

    public string Fio { get; set; } = null!;

    public string Phone { get; set; } = null!;

    public int UserId { get; set; }

    public virtual ICollection<EquipmentRequest> EquipmentRequests { get; set; } = new List<EquipmentRequest>();

    public virtual User User { get; set; } = null!;
}
