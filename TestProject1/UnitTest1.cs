using System.ComponentModel.DataAnnotations;
using NUnit.Framework;
using Assert = NUnit.Framework.Assert;
using Microsoft.Azure.Documents;
using pz_18;

namespace TestProject1
{
    public class UnitTest1
    {
        [Fact]
        public void Test1()
        {

        }
    }
    [TestFixture]
    public class UserTests
    {
        [Test]
        public void Validate_User_With_Invalid_Data_Should_Fail()
        {
            
            var user = new Users
            {
                Llogin = "",
                Ppassword = "short"
            }; 

            
            var results = new List<ValidationResult>();
            var validationContext = new ValidationContext(user);
            var isValid = Validator.TryValidateObject(user, validationContext, results, true);

            Assert.That(isValid, Is.False);
            Assert.That(results.Count > 0, Is.True);
        }
    }
}
