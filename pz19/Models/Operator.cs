using System;
using System.Collections.Generic;

namespace pz19.Models;

public partial class Operator
{
    public int OperatorsId { get; set; }

    public string Fio { get; set; } = null!;

    public string Phone { get; set; } = null!;

    public int UserId { get; set; }

    public virtual User User { get; set; } = null!;
}
