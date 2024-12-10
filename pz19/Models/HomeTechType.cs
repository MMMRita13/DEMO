using System;
using System.Collections.Generic;

namespace pz19.Models;

public partial class HomeTechType
{
    public int HomeTechTypeId { get; set; }

    public string Nname { get; set; } = null!;

    public virtual ICollection<HomeTechModel> HomeTechModels { get; set; } = new List<HomeTechModel>();
}
