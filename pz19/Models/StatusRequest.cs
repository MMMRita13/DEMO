using System;
using System.Collections.Generic;

namespace pz19.Models;

public partial class StatusRequest
{
    public int StatusRequestsId { get; set; }

    public string Nname { get; set; } = null!;

    public virtual ICollection<EquipmentRequest> EquipmentRequests { get; set; } = new List<EquipmentRequest>();

    public virtual ICollection<Request> Requests { get; set; } = new List<Request>();
}
