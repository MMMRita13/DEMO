using System;
using System.Collections.Generic;

namespace pz19.Models;

public partial class User
{
    public int UserId { get; set; }

    public string Llogin { get; set; } = null!;

    public string Ppassword { get; set; } = null!;

    public virtual ICollection<Client> Clients { get; set; } = new List<Client>();

    public virtual ICollection<Manager> Managers { get; set; } = new List<Manager>();

    public virtual ICollection<Master> Masters { get; set; } = new List<Master>();

    public virtual ICollection<Operator> Operators { get; set; } = new List<Operator>();
}
