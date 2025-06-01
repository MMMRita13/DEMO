using System;
using System.Collections.Generic;
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
    /// Логика взаимодействия для MainWindow2.xaml
    /// </summary>
    public partial class MainWindow2 : Window
    {
        RemontEntities db = new RemontEntities();
        int userID;
        public MainWindow2(int userID)
        {
            InitializeComponent();
            this.userID = userID;
            LoadRequests();
           
        }
        private void LoadRequests()
        {
            ClientDB.ItemsSource = db.Request.Include("carModel").Include("requestStatus").Include("User").ToList();
        }

        private void AddButton_Click(object sender, RoutedEventArgs e)
        {
           
            AddRequest order = new AddRequest(userID);
            order.ShowDialog();
            LoadRequests();
            db.SaveChanges();
        }
    }
}
