using System;
using System.Collections.Generic;

namespace pz19.Models;

public partial class Request
{
    public int RequestId { get; set; }

    public DateTime StartDate { get; set; }

    public int ModelId { get; set; }

    public string ProblemDescription { get; set; } = null!;

    public int StatusRequest { get; set; }

    public DateTime CompletionDate { get; set; }

    public string RepairParts { get; set; } = null!;

    public int MasterId { get; set; }

    public Guid ClientId { get; set; }

    public virtual Client Client { get; set; } = null!;

    public virtual ICollection<Comment> Comments { get; set; } = new List<Comment>();

    public virtual ICollection<EquipmentRequest> EquipmentRequests { get; set; } = new List<EquipmentRequest>();

    public virtual Master Master { get; set; } = null!;

    public virtual HomeTechModel Model { get; set; } = null!;

    public virtual StatusRequest StatusRequestNavigation { get; set; } = null!;


}
