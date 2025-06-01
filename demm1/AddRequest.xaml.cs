using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace demm1
{
    /// <summary>
    /// Логика взаимодействия для AddRequest.xaml
    /// </summary>
    public partial class AddRequest : Window
    {
        RemontEntities db = new RemontEntities();
        int userID;
        public AddRequest(int userID)
        {
            
            InitializeComponent();
           ModelCB.ItemsSource = db.carModel.ToList();
            this.userID = userID;
        }
        
        private void Button_Click(object sender, RoutedEventArgs e)
        {
            if(ModelCB.SelectedItem is carModel model)
            {
                var order = new Request{
                    startDate = DateTime.Now,
                    carModel = model,
                    problemDescryption = ProblemTB.Text,
                    requestStatusID = 1,
                    clientID = userID
                };
                db.Request.Add(order);
               
                db.SaveChanges();

                MessageBox.Show("Заказ обновлён.");
                this.Close();
            }
        }
    }
}
