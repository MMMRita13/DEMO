using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace pz_18
{
    public interface IMasters : INotifyPropertyChanged
    {
        Guid MasterId { get; }
        string FIO { get; set; }
        string Phone { get; set; }
    }
}
