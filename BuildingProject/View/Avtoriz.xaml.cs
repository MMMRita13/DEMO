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

namespace BuildingProject
{
    /// <summary>
    /// Логика взаимодействия для Avtoriz.xaml
    /// </summary>
    public partial class Avtoriz : Window
    {
        ConstructionProjectEntities db;
        MainWindow mw;
        
        public Avtoriz()
        {
            InitializeComponent();
            db = new ConstructionProjectEntities();
            mw = new MainWindow();
            
        }

        private void Ok_Click(object sender, RoutedEventArgs e)
        {
            db =  ConstructionProjectEntities.GetContext();
            var user = db.Users.Where(d=>d.Role_ == tbL.Text && d.Pasword == tbP.Password).FirstOrDefault();
        
            if (user != null)
            {

                if (user.Role_.Equals("Администратор"))
                {
                    mw.Show();
                    this.Close();
                }
                else if (user.Role_.Equals("Сотрудник"))
                {
                    WorkerWindow worker = new WorkerWindow();
                    
                    worker.Show();
                    this.Close();
                }
            }
            else
            {
                MessageBox.Show("Введенные данные не верны");
            }

            

            LoginValidator loginValidator = new LoginValidator();
            PasswordValidator passwordValidator = new PasswordValidator();

            loginValidator.SetNext(passwordValidator);

            bool resulr = loginValidator.Validate(user);

            Console.WriteLine(resulr);
        }

        private void Cancel_Click(object sender, RoutedEventArgs e)
        {
            this.Close();

        }

        private void tbL_TextChanged(object sender, TextChangedEventArgs e)
        {

        }

        private void tbP_TextChanged(object sender, TextChangedEventArgs e)
        {

        }

        private void сbL_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {

        }


       

        private void Window_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            this.DragMove();
        }

        private void btnMinimize_Click(object sender, RoutedEventArgs e)
        {
            WindowState = WindowState.Minimized;
        }

        private void btnClose_Click(object sender, RoutedEventArgs e)
        {
            Application.Current.Shutdown();
        }
    }
}
