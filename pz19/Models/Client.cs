using System;
using System.Collections.Generic;

namespace pz19.Models;


public partial class Client
{
    public Guid ClientId { get; set; }

    public string Fio { get; set; } = null!;

    public string Phone { get; set; } = null!;

    public int UserId { get; set; }

    public virtual ICollection<Request> Requests { get; set; } = new List<Request>();

    public virtual User User { get; set; } = null!;
}
