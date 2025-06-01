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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace demm1
{
    /// <summary>
    /// Логика взаимодействия для MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        RemontEntities db = new RemontEntities();
        public MainWindow()
        {
            InitializeComponent();
            LoadRequests();
        }
        private  void LoadRequests(){
            RequestsGrid.ItemsSource = db.Request.Include("requestStatus").Include("User").Include("carModel").ToList();
}

        private void EditButton_Click(object sender, RoutedEventArgs e)
        {
           
            if (RequestsGrid.SelectedItem is Request selectedRequest)
            {
                EditWindow add = new EditWindow(selectedRequest);
                add.ShowDialog();
                
                db.SaveChanges();
                LoadRequests();
            }
            else
            {
                MessageBox.Show("Выберите заказ для редактирования.");
            }

        }

        private void SaveButton_Click(object sender, RoutedEventArgs e)
        {
             var requests = RequestsGrid;

            PrintDialog pd = new PrintDialog();
            if (pd.ShowDialog() == true)
            {
                pd.PrintVisual(requests, "Проект");
            }
        }

        private void DelButton_Click(object sender, RoutedEventArgs e)
        {
            if (RequestsGrid.SelectedItem is Request request)
            {
                var order = db.Request.First(r => r.ID == request.ID);

                if (order.requestStatus.Name == "Готов к выдаче")
                {
                    MessageBox.Show("Невозможно удалить заказ");
                    return;
                }

                if (order != null)
                {
                    db.Request.Remove(order);
                    db.SaveChanges();

                    LoadRequests();

                    MessageBox.Show("Заказ удален");
                }
            }
        }

        private void Search_TextChanged(object sender, TextChangedEventArgs e)
        {
            string searchText = Search.Text.ToLower();

            var filter = db.Request.Where(r =>(r.carModel.Name ?? "").ToLower().Contains(searchText)).ToList();
            RequestsGrid.ItemsSource = filter;
        }
    }
}
