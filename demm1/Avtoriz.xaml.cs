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
    /// Логика взаимодействия для Avtoriz.xaml
    /// </summary>
    public partial class Avtoriz : Window
    {
        RemontEntities db = new RemontEntities();
        public Avtoriz()
        {
            InitializeComponent();
        }

        

        private void SignButton_Click(object sender, RoutedEventArgs e)
        {
            
                User user = db.User.Where(l =>l.Login == LoginTB.Text && l.Password == PassB.Password).FirstOrDefault();
            
            var role = user.Type;
            if (user == null)
            {
                return;
            }
            if (role.Name == "Автомеханик")
            {     
                MainWindow mainWindow = new MainWindow();
            mainWindow.Show();
                this.Close();
            }
            else if (role.Name == "Менеджер")
            {
                MainWindow mainWindow = new MainWindow();
                mainWindow.Show();
                this.Close();
            }
            else if (role.Name == "Оператор")
            {
                MainWindow mainWindow = new MainWindow();
                mainWindow.Show();
                this.Close();
            }
            else if (role.Name == "Заказчик")
            {
                

                MainWindow2 mainWindow2 = new MainWindow2(user.ID);
                mainWindow2.Show();
                this.Close();
            }
            else
            {
                MessageBox.Show("Неверно введен пароль!");
                return;
            }
        }
    }
}
