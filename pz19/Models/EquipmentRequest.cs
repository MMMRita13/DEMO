using System;
using System.Collections.Generic;

namespace pz19.Models;

public partial class EquipmentRequest
{
    public int EquipmentRequestId { get; set; }

    public string? ProblemDescription { get; set; }

    public int Status { get; set; }

    public int RequestId { get; set; }

    public int MasterId { get; set; }

    public int ManagerId { get; set; }

    public virtual Manager Manager { get; set; } = null!;

    public virtual Master Master { get; set; } = null!;

    public virtual Request Request { get; set; } = null!;

    public virtual StatusRequest StatusNavigation { get; set; } = null!;
}
