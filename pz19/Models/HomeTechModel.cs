using System;
using System.Collections.Generic;

namespace pz19.Models;

public partial class HomeTechModel
{
    public int HomeTechModelId { get; set; }

    public string Nname { get; set; } = null!;

    public int TypeId { get; set; }

    public virtual ICollection<Request> Requests { get; set; } = new List<Request>();

    public virtual HomeTechType Type { get; set; } = null!;
}
