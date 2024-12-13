using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace pz19.ViewModels
{
    public  class ValidableClient : ValidableBindableBase
    {
        private int _id;
        public int Id { get => _id; set => SetProperty(ref _id, value); }

        private string _fio;
        [Required]
        public string Fio { get => _fio; set => SetProperty(ref _fio, value);}

        

        private string _phone;
        [Phone]
        public string Phone { get => _phone; set => SetProperty(ref _phone, value); }

        
    }
}
