using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BuildingProject
{
    internal interface IValidator
    {
        void SetNext(IValidator validator);
        bool Validate(Users user);
    }
}
