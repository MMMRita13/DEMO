using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BuildingProject
{
    internal class LoginValidator : IValidator
    {
        private IValidator _validator;
        public void SetNext(IValidator validator)
        {
            _validator = validator;
        }

        public bool Validate(Users user)
        {
            if(String.IsNullOrEmpty(user.Role_) || user.Role_.Length < 5)
            {
                Console.WriteLine("длина имени пользователя не соответствует требованиям");
                return false;
            }

            return _validator?.Validate(user) ?? true;
        }
    }
}
