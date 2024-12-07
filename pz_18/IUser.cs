using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace pz_18
{
    public interface IUser : INotifyPropertyChanged
    {
        Guid UserId { get; }
        string Login { get; set; }
        string Password { get; set; }
        ICollection<IMasters> Masters { get; }
    }


}
