//------------------------------------------------------------------------------
// <auto-generated>
//     Этот код создан по шаблону.
//
//     Изменения, вносимые в этот файл вручную, могут привести к непредвиденной работе приложения.
//     Изменения, вносимые в этот файл вручную, будут перезаписаны при повторном создании кода.
// </auto-generated>
//------------------------------------------------------------------------------

namespace pz_18
{
    using System;
    using System.Collections.Generic;
    
    public partial class Operators
    {
        public int OperatorsID { get; set; }
        public string FIO { get; set; }
        public string Phone { get; set; }
        public int UserID { get; set; }
    
        public virtual Users Users { get; set; }
    }
}
