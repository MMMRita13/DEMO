   M S B A M L   `   `   `  ����?  ;pz19, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null'clr-namespace:pz19.Views
pz19.Views  5      )clr-namespace:pz19.Modelspz19.Models  5      1clr-namespace:pz19.Converterspz19.Converters  6B   "    pz19.Views.AddEditClientView   5      W SPresentationCore, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35e aSystem.Windows.Controls.Ribbon, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089R NWindowsBase, Version=8.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35F 9http://schemas.microsoft.com/winfx/2006/xaml/presentation     5      R NSystem.Xaml, Version=8.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5      Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5      @d2http://schemas.microsoft.com/expression/blend/2008     5      *localclr-namespace:pz19.Views     5      *dataclr-namespace:pz19.Models     5      3	converterclr-namespace:pz19.Converters     6B     � 	Resources  5      0  *pz19.Converters.ValueToVisibilityConverter   VisConverter  NegVisConverter   Negate �� 
TargetType �� Triggers P� Property P� Value �� Property �� Value �� Self	 �� RelativeSource
 �� Path��%!  &	        &	      '�� 1       5   
   6/     5   
   $
 True��6G   �� 5   
   " ��6    5      P� 5       �@=�HasError$
 True��69   . �5      ��  �@�ToolTip 5      �� #	 Z $1
 +(Validation.Errors).ClientItem.ErrorContent�5      5      5      .��5      �  � RowDefinitions 5   
   �� 5      $
��auto �6   �� 5      $
��auto �6   �� 5      $
��auto �6   .+�5!   
   � -   5        $��
ClientGrid��5!      $��0��6!   $��5q�5#      $��Center=�5$       � DataContext 5"      �� *Client+ 5&      �� 5'      $
��auto �6    �� 5(      $
��auto �6    �� 5)      $
��auto �6    �� 5*      $
��auto �6     � ColumnDefinitions 5,      �� 5-      $
��auto �6#   �� 5.      $
��auto �6#   .+�52      �� $��Имя:��6   $��0��53      $��0��54      $��Center=�55      $��Right=�56      $��3q�57      �� 58      $��0��59      $��1��5:      $	��200��5;      $��3q�5<      ��58      �� *Fio+" �� ValidatesOnNotifyDataErrors$
 True��5<      �� 5B      $��Телефон:��6   $��2��5C      $��0��5D      $��Center=�5E      $��Right=�5F      $��3q�5G      �� 5H      $��2��5I      $��1��5J      $	��200��5K      $��3q�5L      ��5H      �� *Phone+$
 True��5L      5O      � 5Q   
   -   5        $��CommandButtonsGrid��5Q      $��1��6)   $��10q�5R      .+�5S      �� $��отмена��6   $	��250��5U      ��5T      �� *CancelCommand+5U      �� 5V      $��добавить��6   $	��250��5Y      ��5W      �� *SaveCommand+y�5X      �� *IsEditeMode+ �� 	Converter# [ 5Y      �� 5Z      $��сохранить��6   $	��250��5]      ��5[      �� *SaveCommand+y�5\      �� *IsEditeMode+# [  5]      5`      � 5b   
   -   5        $��CommandButtonsGrid2��5b      $��2��6*   $��10q�5c      .+�5d      �� $��отмена��6   $	��250��5f      ��5e      �� *CancelCommand+5f      5j      5l      5m      