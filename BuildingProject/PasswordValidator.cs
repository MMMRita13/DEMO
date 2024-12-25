using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BuildingProject
{
    internal class PasswordValidator : IValidator
    {
        private IValidator _validator;
        public void SetNext(IValidator validator)
        {
            _validator = validator;
        }

        public bool Validate(Users user)
        {
            if(String.IsNullOrEmpty(user.Pasword) || user.Pasword.Length <= 3)
            {
                Console.WriteLine("пароль не соответствует требуемой длине");
                return false;
            }
            if(!user.Pasword.Any(Char.IsDigit))
            {
                Console.WriteLine("в пароле отсутствуют цифры");
                return false ;
            }
            if(!user.Pasword.Any(Char.IsUpper))
            {
                Console.WriteLine("в пароле нет символов в верхнем регистре");
                return false;
            }
            return _validator?.Validate(user) ?? true;
        }
    }
}
